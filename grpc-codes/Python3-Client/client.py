from __future__ import print_function

import argparse
import sys
import proto.stt_pb2_grpc as stt_pb2_grpc
import grpc
import proto.stt_pb2 as stt_pb2
import time
import wave
from configparser import ConfigParser

#Reading from config file
parser = ConfigParser()
parser.read('user.config')

_TIMEOUT_SECONDS = 10
_TIMEOUT_SECONDS_STREAM = 1000

class Sender:

	def clientChunkStream(self, service, filename,token,accesskey,encoding,lang_code,audioformat, chunkSize=1024):

		def request_stream():
			for item in self.generate_chunks(filename, grpc_on=True, chunkSize=chunkSize):
				yield item
				
		responses=service.DoSpeechToText(request_stream(),_TIMEOUT_SECONDS_STREAM,metadata=(('token',token),('lang',lang_code),('accesskey',accesskey),('audioformat',audioformat),('encoding',encoding)))
		
		for response in responses:
			print(response.transcript)

	def createService(self, ipaddr, port):
		'''
			SSL Configuration goes here.
			Paste the 'cert.pem' mailed to you in the root directory.
		'''
		ca_cert = 'cert.pem'
		root_certs = open(ca_cert,'rb').read()
		credentials = grpc.ssl_channel_credentials(root_certs)
		channel = grpc.secure_channel(ipaddr + ':' + str(port), credentials)
		return stt_pb2_grpc.ListenerStub(channel)

	# create an iterator that yields chunks in raw or grpc format
	def generate_chunks(self,filename, grpc_on=False, chunkSize=1280):
		# raw byte file
		if '.raw' in filename:
			f = open(filename, 'rb')
			while True:
				chunk = f.read(chunkSize)
				if chunk:
					if grpc_on:
						yield stt_pb2.SpeechChunk(content=chunk)
					else:
						yield chunk
				else:
					raise StopIteration

		# wav file format
		elif '.wav' in filename:
			audio = wave.open(filename)
		if audio.getsampwidth() != 2:
			print('%s: wrong sample width (must be 16-bit)' % filename)
			raise StopIteration
		if audio.getframerate() != 8000 and audio.getframerate() != 16000:
			print('%s: unsupported sampling frequency (must be either 8 or 16 khz)' % filename)
			raise StopIteration
		if audio.getnchannels() != 1:
			print('%s: must be single channel (mono)' % filename)
			raise StopIteration

		while True:
			chunk = audio.readframes(chunkSize // 2)  # each wav frame is 2 bytes
			if chunk:

				if grpc_on:
					sent=chunk
					time.sleep(0.040)
					yield stt_pb2.SpeechChunk(content=chunk)
				else:
					yield chunk
			else:

				self.last_chunk_time=time.time()

				raise StopIteration

if __name__ == '__main__':

	senderObj = Sender()

	#API URL
	service = senderObj.createService("asr.gnani.ai", 443)

	'''
		Set your token , accesskey , encoding , lang_code , audioformat in the config map.
		Sample audio sent from audio/ folder. You can send your own audio.
	'''
	token=parser.get('USER', 'TOKEN')
	accesskey=parser.get('USER', 'ACCESSKEY')
	encoding=parser.get('USER', 'ENCODING')
	lang_code=parser.get('USER', 'LANGUAGE_CODE')
	audioformat=parser.get('USER', 'AUDIOFORMAT')

	senderObj.clientChunkStream(service,"audio/english.wav",token,accesskey,encoding,lang_code,audioformat,1280)




