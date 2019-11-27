var request = require('request')
var fs = require('fs')
var config = require('./config/config');

/**
 * Method to upload audio file on server for speech to text conversion
 * 
 * @param {object} options Contains the api url, header details
 * @param {string} audioFilePath path to audio file
 * @returns Promise success or failure according to the response from server for audio file status
 */
function uploadRequest(options, audioFilePath) {
    return new Promise(function (success, failure) {
        var r = request.post(options, function optionalCallback(err, response, body) {
            if (!err && response.statusCode == 200) {
                var jsonResponse = JSON.parse(body)
                if (jsonResponse.status == 'success') {
                    console.log('Audio File uploading is successful!')
                    console.log('The transcript_key generated for audio :' + jsonResponse.requestid)
                    success(jsonResponse.requestid);
                } else {
                    failure("Sorry, your file is not uploaded successfully !");
                }
            } else {
                failure(err);
            }
        });
        var form = r.form()
        form.append('audio_file', fs.createReadStream(audioFilePath))
    });
}

/**
 * Method to get the status of the uploaded file
 * 
 * @param {object} options Contains the api url, header details
 * @returns Promise success or failure according to the response from server for audio file status
 */
function statusRequest(options) {
    return new Promise(function (success, failure) {
        var counter = 1;
        //requesting status of the uploaded file every minute
        var requestLoop = setInterval(function () {
            request.post(options, function optionalCallback(err, response, body) {
                //the request loop will work till 10 minutes
                if (counter == 10) {
                    clearInterval(requestLoop);
                    failure("Sorry, there is some issue while getting your response!");
                }
                counter++;
                if (!err && response.statusCode == 200) {
                    var jsonResponse = JSON.parse(body)
                    if (jsonResponse.status == 'in-progress') {
                        console.log('Decoding in progress...')
                    } else if (jsonResponse.status == 'success') {
                        clearInterval(requestLoop);
                        success(body);
                    } else {
                        clearInterval(requestLoop);
                        failure("Sorry, the file is not converted successfully!");
                    }
                } else {
                    clearInterval(requestLoop);
                    console.log(err)
                    failure("Sorry, there is some issue while getting your response!");
                }
            });
        }, 60000);
    });
}
/**
 * Main Method  
 * 
 * Setting the language code, audiofile path, options object for rest requests
 * Requesting audio file upload using uploadRequest
 * Requesting status for audio file using statusRequest
 * 
 * @returns JSON result
 */
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

            // object containing the request details including api url, headers and certificate file
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

                // result contains the transcript_key 
                transcript_key = result;
                // object containing the request details including api url, headers and certificate file
                var statusOptions = {
                    url: config.API_URL_STATUS,
                    headers: {
                        'token': config.TOKEN,
                        'lang': config.LANGUAGE_CODE,
                        'accesskey': config.ACCESSKEY,
                        'audioformat': config.AUDIOFORMAT,
                        'encoding': config.ENCODING,
                        'requestid':transcript_key
                    },
                    cert: fs.readFileSync(config.CERT_FILE_PATH),
                    rejectUnauthorized: false,
                };
                console.log("Please wait for a few moments, gnani.ai is working!")
                //checking the status of audio file after uploading
                return statusRequest(statusOptions);
            }).then(function (result) {
                // Audio to text result 
                console.log("Audio to text result :")
                console.log(result);
            }).catch(function (error) {
                console.log(error);
            });
        }
    } catch (error) {
        console.log(error.message);
    }
}
//main method called here
main()
