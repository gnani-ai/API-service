import java.io.*;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.protobuf.ByteString;

import SpeechToText.ListenerGrpc;
import SpeechToText.ListenerGrpc.ListenerBlockingStub;
import SpeechToText.ListenerGrpc.ListenerStub;
import SpeechToText.Stt;
import io.grpc.*;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.StreamObserver;


public class GrpcClient {

	//Instantiation of Logger, Channel and (blocking and Async) Stubs
	private static final Logger logger = Logger.getLogger(GrpcClient.class.getName());
	private final ManagedChannel channel;
	private final ListenerBlockingStub blockingStub;
	private final ListenerStub asyncStub;

	//Initialize the buffersize
	private int buffersize = 1280;


	/*
	-- Created GrpcClient constructor and passed the “api_url” and “port”
	-- Created a nettychannelbuilder (for TLS) and passed the host and port and created  ssl connection and passed the “cert.pem” ssl certificate to build the connection.
	-- Which calls another GrpcClient constructor that takes Managedchannelbuilder obj. as parameter :
	-- Channel takes Managedchannelbuilder obj. Build and is passed as param in blocking and async stub
	-- And async stub callscredentials for creating metadata headers obj. that takes the metadata of the “config.properties” file contents/properties and pass it to header of metadata.
 	*/
	public static void main (String [] args) throws Exception {

        Properties p = GrpcClient.loadProperties();
        GrpcClient grpcClient = new GrpcClient(p.getProperty("API_URL"), 443);

        grpcClient.callFunction();
    }

	private final Properties p = loadProperties();

	//GrpcClient parameterized constructor with URL and Port
	//Building a sslconnection[secured] 
	public GrpcClient(String host, int port) throws Exception {
		this(NettyChannelBuilder.forAddress(host, port).sslContext(GrpcSslContexts.forClient().trustManager(GrpcClient.class.getResourceAsStream("cert.pem")).build()));
	}
	
	//GrpcClient parameterized constructor with ManagedChannelBuilder 
	public GrpcClient(ManagedChannelBuilder<?> channelBuilder) {
		channel = channelBuilder.build();

		blockingStub = ListenerGrpc.newBlockingStub(channel);

		//Metadata Headers
		asyncStub = ListenerGrpc.newStub(channel).withCallCredentials(new CallCredentials() {

			@Override
			public void applyRequestMetadata(RequestInfo requestInfo, Executor appExecutor, MetadataApplier applier) {
				appExecutor.execute(new Runnable(){
					@Override
					public void run() {
						try {
							Metadata headers = new Metadata();
							Metadata.Key<String> clientIdKey = Metadata.Key.of("token", Metadata.ASCII_STRING_MARSHALLER);
							headers.put(clientIdKey, p.getProperty("TOKEN"));


							clientIdKey = Metadata.Key.of("lang", Metadata.ASCII_STRING_MARSHALLER);
							headers.put(clientIdKey, p.getProperty("LANGUAGE_CODE"));


							clientIdKey = Metadata.Key.of("accesskey", Metadata.ASCII_STRING_MARSHALLER);
							headers.put(clientIdKey, p.getProperty("ACCESSKEY"));


							clientIdKey = Metadata.Key.of("audioformat", Metadata.ASCII_STRING_MARSHALLER);
							headers.put(clientIdKey, p.getProperty("AUDIOFORMAT"));

							clientIdKey = Metadata.Key.of("encoding", Metadata.ASCII_STRING_MARSHALLER);
							headers.put(clientIdKey, p.getProperty("ENCODING"));
							
							clientIdKey = Metadata.Key.of("filename", Metadata.ASCII_STRING_MARSHALLER);
							headers.put(clientIdKey, p.getProperty("AUDIO_FILE"));

							applier.apply(headers);
						} catch(Throwable ex) {
							applier.fail(Status.UNAUTHENTICATED.withCause(ex));
						}
					}
				});
			}

			@Override
			public void thisUsesUnstableApi() {

			}
		});
	}

	/*
	-- Creates a StreamObserver response obj. that returns the stt-stub TranscriptChunk 
	-- Overrides the onNext, onError, onCompleted methods 
	-- onNext gets transcript from TranscriptChunk and returns it.
	-- Creates a StreamObserver request obj. that takes ayncstub dospeechtotextmethod passed by response param [asyncStub.doSpeechToText(response);] that returns stt.speechchunk.
	-- Stt.speechchunk obj. sets token, lang, and content and then builds it and then the obj. Is passed to onNext method call.
	-- Setting content converts the audiofile to bytestring (bytes)
	## ByteString as an immutable byte array.
	*/

	public void callFunction () throws Exception {

		// CountDownLatch to check that the server has completed on its side
		final CountDownLatch finishLatch = new CountDownLatch(1);

		
		StreamObserver<Stt.TranscriptChunk> response = new StreamObserver<Stt.TranscriptChunk>() {
			@Override
			public void onNext(Stt.TranscriptChunk transcriptChunk) {
				System.out.println(transcriptChunk.getTranscript());
			}

			@Override
			public void onError(Throwable throwable) {
				logger.log(Level.WARNING, "Transcription failed: " + throwable.getMessage() +"\n STATUS: "+ Status.fromThrowable(throwable));
				finishLatch.countDown();
			}

			@Override
			public void onCompleted() {
				finishLatch.countDown();
				channel.shutdown();
			}
		};
		

		StreamObserver<Stt.SpeechChunk> request =  asyncStub.doSpeechToText(response);
		

		try {

			InputStream is = null;
			is = GrpcClient.class.getResourceAsStream(p.getProperty("AUDIO_FILE"));
			byte[] byteArray = new byte[buffersize];
			BufferedInputStream buf = new BufferedInputStream(is);

			while (buf.read(byteArray) > 0){
				Stt.SpeechChunk re = Stt.SpeechChunk.newBuilder()
						.setLang(p.getProperty("LANGUAGE_CODE"))
						.setToken(p.getProperty("TOKEN"))
						.setContent(ByteString.copyFrom(byteArray))
						.build();

				request.onNext(re);
			}

			is.close();

		} catch (RuntimeException e) {
			request.onError(e);
			throw e;
		}

		request.onCompleted();

		finishLatch.await(1, TimeUnit.MINUTES);
		
	}

	//Load properties from "config.prop" file
	public static Properties loadProperties () {
		Properties prop = new Properties();
		InputStream is = null;
		try {
			is = GrpcClient.class.getResourceAsStream("config.properties");
		} catch (Exception ex) {
			logger.log(Level.SEVERE, ex.getMessage());
		}
		try {
			prop.load(is);
		} catch (IOException ex) {
		}
		return prop;
	}

}
