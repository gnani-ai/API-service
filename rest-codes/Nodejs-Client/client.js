var request = require('request')
var fs = require('fs')
var config = require('./config/config');

//function to upload the audio file on server
function uploadRequest(options, audioFilePath) {
    return new Promise(function (success, failure) {
        var r = request.post(options, function optionalCallback(err, response, body) {
            if (!err && response.statusCode == 200) {
                console.log('Audio File uploading is successful!')
                success(body);
            } else {
                failure(err);
            }
        });
        var form = r.form()
        form.append('audio_file', fs.createReadStream(audioFilePath))
    });
}

//function to get the status of the uploaded file 
function statusRequest(options) {
    return new Promise(function (success, failure) {
        var counter = 1;
        //requesting status of the uploaded file every 10 seconds
        var requestLoop = setInterval(function () {
            request.post(options, function optionalCallback(err, response, body) {
                //the request loop will work till 10 minutes
                if (counter == 120) {
                    clearInterval(requestLoop);
                    failure("Sorry, there is some issue while getting your response!");
                }
                counter++;
                if (!err && response.statusCode == 200) {
                    if (body != 'Decoding in progress') {
                        clearInterval(requestLoop);
                        success(body);
                    }
                    console.log(body)
                } else {
                    clearInterval(requestLoop);
                    failure("Sorry, there is some issue while getting your response!");
                }
            });
        }, 10000);
    });
}

function main() {
    try {
        var langCode = config.LANGUAGE_CODE;
        var audioFilePath = '';

        switch (langCode) {
            case "tam_IN":
                audioFilePath = config.TAM_AUDIO;
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
            case "eng_IN":
                audioFilePath = config.ENG_AUDIO;
                break;
            default:
                audioFilePath = config.ENG_AUDIO;
        }

        // Check that the file exists locally
        if (!fs.existsSync(audioFilePath)) {
            console.log("Audio file not found");

        } else {

            var options = {
                url: config.API_URL_UPLOAD,
                headers: {
                    'token': config.TOKEN,
                    'lang': config.LANGUAGE_CODE,
                    'accesskey': config.ACCESSKEY,
                    'audioformat': config.AUDIOFORMAT,
                    'encoding': config.ENCODING,
                },
                cert: fs.readFileSync(config.CERT_FILE_PATH),
                rejectUnauthorized: false
            };
            //uploading the audio file on server
            uploadRequest(options, audioFilePath).then(function (result) {
                // console.log('got the response')
                transcript_key = result;
                // console.log(result);
                var statusOptions = {
                    url: config.API_URL_STATUS + "?transcript_key=" + transcript_key,
                    headers: {
                        'token': config.TOKEN,
                        'lang': config.LANGUAGE_CODE,
                        'accesskey': config.ACCESSKEY,
                        'audioformat': config.AUDIOFORMAT,
                        'encoding': config.ENCODING
                    },
                    cert: fs.readFileSync(config.CERT_FILE_PATH),
                    rejectUnauthorized: false,
                };
                //checking the status of audio file after uploading
                return statusRequest(statusOptions);
            }).then(function (result2) {
                return result2;
            }).catch(function (error) {
                console.log(error);
            });
        }
    } catch (error) {
        console.log(error.message);
    }
}

main()