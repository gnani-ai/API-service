const grpc = require('grpc');
const protoLoader = require('@grpc/proto-loader');
var config = require('./config/config'); //configuration file 
var fs = require('fs');

const packageDefinition = protoLoader.loadSync(
        __dirname + '/proto/stt.proto',
        {
            keepCase: true,
            longs: String,
            enums: String,
            defaults: true,
            oneofs: true
        }
);
const protoDescriptor = grpc.loadPackageDefinition(packageDefinition);
const proto = protoDescriptor.SpeechToText;

function main() {
    try {

        //ssl credentials for secure grpc connection
        var ssl_creds = grpc.credentials.createSsl(fs.readFileSync(config.CERT_FILE_PATH));

        //nodejs Stub for grpc protocol
        const client = new proto.Listener(config.API_URL, ssl_creds);

        //meta data object for grpc protocol request
        const meta = new grpc.Metadata();
        meta.add('token', config.TOKEN);
        meta.add('lang', config.LANGUAGE_CODE);
        meta.add('accesskey', config.ACCESSKEY);
        meta.add('audioformat', config.AUDIOFORMAT);
        meta.add('encoding', config.ENCODING);

        var langCode = config.LANGUAGE_CODE;
        var audioFilePath = '';

        switch (langCode) {
            case "eng_IN":
                audioFilePath = config.ENG_AUDIO;
                break;
            case "kan_IN":
                audioFilePath = config.KAN_AUDIO;
                break;
            case "hin_IN":
                audioFilePath = config.HIN_AUDIO;
                break;
            case "tel_IN":
                audioFilePath = config.TEL_AUDIO;
                break;
            case "guj_IN":
                audioFilePath = config.GUJ_AUDIO;
                break;
            default:
                audioFilePath = config.TAM_AUDIO;
        }
        
        // send audio file name for debugging purpose.
        meta.add('filename', audioFilePath);
            
        // Check that the file exists locally
        if (!fs.existsSync(audioFilePath)) {
            console.log("Audio file not found");

        } else {
            //calling rpc listener
            var call = client.DoSpeechToText(meta);

            //Reading file as stream
            var readStream = fs.createReadStream(audioFilePath, {highWaterMark: 16 * 1024});

            readStream.on('data', function (chunk) {

                //request object SpeechChunk
                var request = {
                    'content': chunk,
                    'token': config.TOKEN,
                    'lang': langCode
                }
                call.write(request);
            }).on('end', function () {

                //response object TranscriptChunk
                call.on("data", (transcriptChunk) => {
                    console.log(transcriptChunk.transcript);
                });

                // error handling
                call.on("error", function (exception) {
                    console.log("Grpc server error :" + exception);
                });

                //closing connection
                call.end();
            });
        }
    } catch (error) {
        console.log(error.message);
    }
}

//calling main function
main();
