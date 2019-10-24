import requests
import time
import logging
from configparser import ConfigParser

''' Reading from config file '''
parser = ConfigParser()
parser.read('user.config')


class Sender:
    ''' logger instantiation '''
    def __init__(self):
        self.logger = logging.getLogger()
        self.logger.setLevel(logging.DEBUG)

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
        response = requests.post(
            url=api_upload_url, verify=cert, files=files, headers=headers)
        if response.status_code != 200:
            response.raise_for_status()
        self.logger.info('File uploaded successfully')
        self.logger.info('The transcript key generated for audio : '+response.text)
        return response.text

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
            PARAMS = {'transcript_key': transcript_key_param}
            headers = {
                'token': token,
                'lang': lang_code,
                'accesskey': accesskey,
                'audioformat': audioformat,
                'encoding': encoding
            }
            response = requests.post(
                url=api_status_url, params=PARAMS, verify=cert, headers=headers)

            if response.status_code == 200:
                if response.text == 'Decoding in progress':
                    self.logger.info('Audio file status : '+response.text)
                    return 'Progress'

                else:
                    self.logger.info(
                        'Audio to text result : '+response.text)
                    return 'Success'
            else:
                self.logger.info(
                    'Sorry, there is some issue while getting your response!')
                return 'Failure'
        except Exception as ex:
            self.logger.error(
                'Failed to get response for the audio file : '+str(ex))


if __name__ == "__main__":

    senderObj = Sender()
    '''
	Set your token , accesskey , encoding , lang_code , audioformat in the user.config file.
	Sample audio sent from audio/ folder. You can send your own audio.
	'''

    token = parser.get('USER', 'TOKEN')
    accesskey = parser.get('USER', 'ACCESSKEY')
    encoding = parser.get('USER', 'ENCODING')
    lang_code = parser.get('USER', 'LANGUAGE_CODE')
    audioformat = parser.get('USER', 'AUDIOFORMAT')
    api_upload_url = parser.get('USER', 'API_UPLOAD')
    api_status_url = parser.get('USER', 'API_STATUS')
    '''
	SSL Configuration goes here.
	Paste the 'chain.pem' mailed to you in the root directory.
	'''
    cert = "chain.pem"
    filePath = 'audio/english.wav'

    try:
        '''Response is saved in "result.log" file '''
        logging.basicConfig(filename="result.log",
                            format='%(asctime)s %(message)s',
                            filemode='w')
        '''uploading audio file on server '''
        transcript_key = senderObj.uploadAudioFile(
            filePath, token, accesskey, encoding, lang_code, audioformat)
        
        for retriescount in range(10):
            time.sleep(60)
            ''' fetching the status of uploaded audio file '''
            result = senderObj.audioFileStatus(
                token, accesskey, encoding, lang_code, audioformat, transcript_key)
            if result == 'Success':
                break
            if result == 'Failure':
                break

    except Exception as ex:
        senderObj.logger.error(str(ex))
