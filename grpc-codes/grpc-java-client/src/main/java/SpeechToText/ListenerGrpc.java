package SpeechToText;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * <pre>
 * The Listener service definition.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: stt.proto")
public final class ListenerGrpc {

  private ListenerGrpc() {}

  public static final String SERVICE_NAME = "SpeechToText.Listener";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<SpeechToText.Stt.SpeechChunk,
      SpeechToText.Stt.TranscriptChunk> getDoSpeechToTextMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DoSpeechToText",
      requestType = SpeechToText.Stt.SpeechChunk.class,
      responseType = SpeechToText.Stt.TranscriptChunk.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<SpeechToText.Stt.SpeechChunk,
      SpeechToText.Stt.TranscriptChunk> getDoSpeechToTextMethod() {
    io.grpc.MethodDescriptor<SpeechToText.Stt.SpeechChunk, SpeechToText.Stt.TranscriptChunk> getDoSpeechToTextMethod;
    if ((getDoSpeechToTextMethod = ListenerGrpc.getDoSpeechToTextMethod) == null) {
      synchronized (ListenerGrpc.class) {
        if ((getDoSpeechToTextMethod = ListenerGrpc.getDoSpeechToTextMethod) == null) {
          ListenerGrpc.getDoSpeechToTextMethod = getDoSpeechToTextMethod = 
              io.grpc.MethodDescriptor.<SpeechToText.Stt.SpeechChunk, SpeechToText.Stt.TranscriptChunk>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "SpeechToText.Listener", "DoSpeechToText"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  SpeechToText.Stt.SpeechChunk.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  SpeechToText.Stt.TranscriptChunk.getDefaultInstance()))
                  .setSchemaDescriptor(new ListenerMethodDescriptorSupplier("DoSpeechToText"))
                  .build();
          }
        }
     }
     return getDoSpeechToTextMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ListenerStub newStub(io.grpc.Channel channel) {
    return new ListenerStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ListenerBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ListenerBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ListenerFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ListenerFutureStub(channel);
  }

  /**
   * <pre>
   * The Listener service definition.
   * </pre>
   */
  public static abstract class ListenerImplBase implements io.grpc.BindableService {

    /**
     */
    public io.grpc.stub.StreamObserver<SpeechToText.Stt.SpeechChunk> doSpeechToText(
        io.grpc.stub.StreamObserver<SpeechToText.Stt.TranscriptChunk> responseObserver) {
      return asyncUnimplementedStreamingCall(getDoSpeechToTextMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getDoSpeechToTextMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                SpeechToText.Stt.SpeechChunk,
                SpeechToText.Stt.TranscriptChunk>(
                  this, METHODID_DO_SPEECH_TO_TEXT)))
          .build();
    }
  }

  /**
   * <pre>
   * The Listener service definition.
   * </pre>
   */
  public static final class ListenerStub extends io.grpc.stub.AbstractStub<ListenerStub> {
    private ListenerStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ListenerStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ListenerStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ListenerStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<SpeechToText.Stt.SpeechChunk> doSpeechToText(
        io.grpc.stub.StreamObserver<SpeechToText.Stt.TranscriptChunk> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getDoSpeechToTextMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * <pre>
   * The Listener service definition.
   * </pre>
   */
  public static final class ListenerBlockingStub extends io.grpc.stub.AbstractStub<ListenerBlockingStub> {
    private ListenerBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ListenerBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ListenerBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ListenerBlockingStub(channel, callOptions);
    }
  }

  /**
   * <pre>
   * The Listener service definition.
   * </pre>
   */
  public static final class ListenerFutureStub extends io.grpc.stub.AbstractStub<ListenerFutureStub> {
    private ListenerFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ListenerFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ListenerFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ListenerFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_DO_SPEECH_TO_TEXT = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ListenerImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ListenerImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_DO_SPEECH_TO_TEXT:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.doSpeechToText(
              (io.grpc.stub.StreamObserver<SpeechToText.Stt.TranscriptChunk>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ListenerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ListenerBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return SpeechToText.Stt.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Listener");
    }
  }

  private static final class ListenerFileDescriptorSupplier
      extends ListenerBaseDescriptorSupplier {
    ListenerFileDescriptorSupplier() {}
  }

  private static final class ListenerMethodDescriptorSupplier
      extends ListenerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ListenerMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ListenerGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ListenerFileDescriptorSupplier())
              .addMethod(getDoSpeechToTextMethod())
              .build();
        }
      }
    }
    return result;
  }
}
