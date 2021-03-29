import requests
import time
import logging
from configparser import ConfigParser
import json
import site; 

import os 
dirr = os.getcwd()
# a = site.getsitepackages()[0]
import gnani_rest_api as _ 
a =  _.__path__[0]

''' Reading from config file '''
parser = ConfigParser()
parser.read('user.config')
api_upload_url = input("Enter API UPLOAD URL: ")
api_status_url = input("Enter API STATUS URL: ")
audio_n = input("Enter the full name of the audio with extention: ")
filePath = dirr + "/"+ audio_n
certificate = input("Enter the name of the certificate with extention: ")
cert = dirr + "/" + certificate
handler = logging.FileHandler('test.log', 'w', 'utf-8')

class Sender:
    ''' logger instantiation '''
    def __init__(self):
        self.logger = logging.getLogger()
        self.logger.setLevel(logging.DEBUG)
        handler = logging.FileHandler('test.log', 'w', 'utf-8')
        formatter = logging.Formatter('%(name)s %(message)s')
        handler.setFormatter(formatter)
        self.logger.addHandler(handler)
        # # self.logger = get_logger(__name__)

    def uploadAudioFile(self, audio_file, token, accesskey, encoding, lang_code, audioformat):
        '''Method uploading audio file for speech to text conversion
            Args:
                audio_file (string): The audio file path
                token (string): Token provided
                accesskey (string): Access key provided
                encoding (string): The encoding type used is pcm16
                lang_code (string): Code of the language according to audio file
                audioformat (string): Format of audio file eg. "wav"
            Raises:
                Exceptions
            Returns:
                response: Response from the server
        '''

        files = {'audio_file': open(audio_file, 'rb')}
        headers = {
            'token': token,
            'lang': lang_code,
            'accesskey': accesskey,
            'audioformat': audioformat,
            'encoding': encoding
        }
        result = ""
        response = requests.post(
            url=api_upload_url, verify=cert, files=files, headers=headers)
        if response.status_code != 200:
            response.raise_for_status()
        
        res = response.json()
        if(res.get('status') == "success"):
            result = res.get('requestid')
            self.logger.info('The transcript key generated for audio : '+ result)
            self.logger.info('File uploaded successfully')
        
        return result


    def audioFileStatus(self, token, accesskey, encoding, lang_code, audioformat, transcript_key_param):
        '''Method getting the status of uploaded audio file for speech to text conversion
            Args:
                audio_file (string): The audio file path
                token (string): Token provided
                accesskey (string): Access key provided
                encoding (string): The encoding type used is pcm16
                lang_code (string): Code of the language according to audio file
                audioformat (string): Format of audio file eg. "wav"
            Raises:
                Exceptions
            Returns:
                response: Response from the server
        '''
        try:
            headers = {
                'token': token,
                'lang': lang_code,
                'accesskey': accesskey,
                'audioformat': audioformat,
                'encoding': encoding,
                'requestid':transcript_key_param
            }
            response = requests.post(
                url=api_status_url, verify=cert, headers=headers)
            if response.status_code == 200:
                res = response.json()
                print(res)
                status = res.get('status')
                if status == 'in-progress':
                    self.logger.info('Audio file status : '+ status)
                    return status
                elif status == 'success':
                    self.logger.info(
                        'Audio to text result : '+response.text)
                    
                    return status
                else :
                    self.logger.info('Audio to text is not successful!')
                    return status
            else:
                self.logger.info(
                    'Sorry, there is some issue while getting your response!')
                return 'Failure'
        except Exception as ex:
            self.logger.error(
                'Failed to get response for the audio file : '+str(ex))


# if __name__ == "__main__":
# print("hello")
def start(token, access_key, encoding, lang_code , formatt):
    print("====================================")

    senderObj = Sender()
    '''
    Set your token , accesskey , encoding , lang_code , audioformat in the user.config file.
    Sample audio sent from audio/ folder. You can send your own audio.
    '''
    token = token
    accesskey = access_key
    encoding = encoding
    lang_code = lang_code
    audioformat = formatt
	# audio_name = a_name

    # api_upload_url = parser.get('USER', 'API_UPLOAD')
    # api_status_url = parser.get('USER', 'API_STATUS')
    '''
    SSL Configuration goes here.
    Paste the 'chain.pem' mailed to you in the root directory.
    '''
    # cert = "chain.pem"

    try:
        '''Response is saved in "result.log" file '''
        h = a + "/result.log"
        logging.basicConfig(filename=h ,encoding='utf-8', mode='w',
                            format='%(asctime)s %(message)s'
                            )

        '''uploading audio file on server '''
        transcript_key = senderObj.uploadAudioFile(
            filePath, token, accesskey, encoding, lang_code, audioformat)
        print("_________________________________________-")
        print(transcript_key)
        if transcript_key is not "":
            for retriescount in range(10):
                time.sleep(60)
                ''' fetching the status of uploaded audio file '''
                result = senderObj.audioFileStatus(
                    token, accesskey, encoding, lang_code, audioformat, transcript_key)
                if result == 'success':
                    print(result)
                    break
                if result == 'fail':
                    break
        else :
            senderObj.logger.info('Audio to text is not successful!') 

    except Exception as ex:
        print(ex)
        senderObj.logger.error(str(ex))

# start()
