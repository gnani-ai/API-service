# Python Client

### Note 
- The rest services are for restricted users only. If you wish to opt for rest services , please contact hello@gnani.ai

## Installation

## Prerequisites
- Make sure that you have Python3.8+ installed on your machine

### Ubuntu
- apt-get install python3.8
- apt-get install python3-pip

### Mac OSX
- `brew install python3`
- `brew install python3-pip`
___
- update details in `user.config` and add certificate file in root directory
- `pip3 install requests`
- `python3 client.py &` 
- check the result in result.log file

## Response Json format

### If language code given in user.config file is hi-IN/hin_IN the response transcript will be in Hindi like below. 

#### NOTE : This is only sample json format

- `{"requestid": "3716878f38ebe7b83b9777198d462390f2580c9c4ee2d79dcb4052ed241b5c15_apiservice_20210222101533038567", "results": [{"alternatives": [{"confidence": "0.69", "endTime": "4.52", "speaker": "1", "startTime": "0.25", "transcript": "देश अलावा राज्य दोनों ही लकवे का नियन्त्रण गांव व्यवस्था नम्बर ले के", "words": [{"confidence": "0.97", "endTime": "0.76", "startTime": "0.37", "word": "देश"}, {"confidence": "0.94", "endTime": "1.24", "startTime": "0.76", "word": "अलावा"}, {"confidence": "0.98", "endTime": "1.54", "startTime": "1.24", "word": "राज्य"}, {"confidence": "0.84", "endTime": "1.81", "startTime": "1.54", "word": "दोनों"}, {"confidence": "0.86", "endTime": "1.93", "startTime": "1.81", "word": "ही"}, {"confidence": "0.31", "endTime": "2.38", "startTime": "1.93", "word": "लकवे"}, {"confidence": "0.82", "endTime": "2.50", "startTime": "2.38", "word": "का"}, {"confidence": "0.46", "endTime": "2.92", "startTime": "2.50", "word": "नियन्त्रण"}, {"confidence": "0.19", "endTime": "3.16", "startTime": "2.92", "word": "गांव"}, {"confidence": "0.67", "endTime": "3.55", "startTime": "3.16", "word": "व्यवस्था"}, {"confidence": "0.57", "endTime": "3.94", "startTime": "3.61", "word": "नम्बर"}, {"confidence": "0.63", "endTime": "4.12", "startTime": "3.94", "word": "ले"}, {"confidence": "0.76", "endTime": "4.39", "startTime": "4.12", "word": "के"}]}]}], "status": "success", "total_transcript": " देश अलावा राज्य दोनों ही लकवे का नियन्त्रण गांव व्यवस्था नम्बर ले के"}`

- Description of keywords in response json
    - confidence : confidence score of the transcription
    - speaker : Based on audio it indicates the speaker (basically person who is speaking) In case of conversation audios you will see speaker : 1 and speaker : 2 like this.
    - startTime and endTime : Duration of the audio corresponds to the transcript chunk.
    - word : Transcription for the above audio duration
    - total_transcript : Transcription for the entire uploaded audio file.

