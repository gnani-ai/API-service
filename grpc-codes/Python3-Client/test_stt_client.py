from __future__ import print_function

import argparse
import sys
import proto.stt_pb2_grpc as stt_pb2_grpc
import grpc
import proto.stt_pb2 as stt_pb2
import time
import wave

class Sender:

	def clientChunkStream(self, service, filename, chunkSize=1024,):

		def request_stream():
			for item in self.generate_chunks(filename, grpc_on=True, chunkSize=chunkSize):
				yield item

	    '''
			Pass the Token , Username , Password and the Lnaguage as headers in the below fields.
		'''		
		responses=service.DoSpeechToText(request_stream(),_TIMEOUT_SECONDS_STREAM,metadata=(('token',token),('lang','hin'),('user_id','111'),('trans_id','gnani_16@gnani.ai;SQLITE;190227160138')))
	
		
		for response in responses:
			print(response.transcript)
			# print("transcription:::",response.transcript.decode("UTF-8","ignore"))


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
	def generate_chunks(self,filename, grpc_on=False, chunkSize=1280,username="",password=""):
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
					yield stt_pb2.SpeechChunk(content=chunk, token=username+password)
				else:
					yield chunk
			else:

				self.last_chunk_time=time.time()

				raise StopIteration

		else:
			raise StopIteration








if __name__ == '__main__':

	senderObj = Sender()

	'''
		API URL goes here.
	'''
	service = senderObj.createService("35.200.216.56", 80)

	'''
		Sample audio sent from audio/ folder. You can send your own audio.
	'''
	senderObj.clientChunkStream(service, "audio/a.wav", 1280)




