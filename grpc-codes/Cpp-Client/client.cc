#include <bits/stdc++.h>
using namespace std;

#include <grpcpp/grpcpp.h>
#include <fstream>
#include "stt.grpc.pb.h"

using grpc::Channel;
using grpc::ClientContext;
using grpc::ClientReaderWriter;
using grpc::Status;

using SpeechToText ::ConfigResult;
using SpeechToText ::ConfigSTT;
using SpeechToText ::Listener;
using SpeechToText ::SpeechChunk;
using SpeechToText ::TranscriptChunk;

#define CONFIG_FILE  "user.config"
#define BUFSIZE  1280

/** structure for storing client context header details from user.config file*/
struct client_configuration
{
  string token;
  string access_key;
  string language;
  string audio_format;
  string endoding;
  string server_url; //server url to connect to
};

/**Static instance for storing details extracted from user.config file */
static struct client_configuration client_config;

/** Init function to read and initialize parameters from user.config file*/
bool grpc_client_init(){
  bool success = true;
  string line;  
  ifstream config_file (CONFIG_FILE);

  if (config_file.is_open())
  {
    //Ignore first 2 lines as they are info 
    getline (config_file,line);
    getline (config_file,line);
    //Get API_URL
    getline (config_file,line);
    client_config.server_url = line.substr(line.find("=") + 1); 
    //Get token
    getline (config_file,line);
    client_config.token = line.substr(line.find("=") + 1); 
    //Get access key
    getline (config_file,line);
    client_config.access_key = line.substr(line.find("=") + 1); 
    //Get encoding
    getline (config_file,line);
    client_config.endoding = line.substr(line.find("=") + 1); 
    //Get language
    getline (config_file,line);
    client_config.language = line.substr(line.find("=") + 1); 
    //Get audio format
    getline (config_file,line);
    client_config.audio_format = line.substr(line.find("=") + 1); 
    //cout<<"Context header"<<client_config.server_url<<endl;
    config_file.close();
  }

  else {
    cerr << "Unable to open file" <<endl;
    success = false;
  }

  return success;
}

class stt_client final : public Listener::Service
{
public:
  stt_client(shared_ptr<Channel> channel) : stub_(Listener::NewStub(channel)) {}

  void DoSpeechToText(string &audioFilePath, int bufferSize)
  {
    ClientContext context;
    setContext(context);

    unique_ptr<ClientReaderWriter<SpeechChunk, TranscriptChunk>> stream(stub_->DoSpeechToText(&context));

    ifstream audioStream(audioFilePath, ifstream::in);

    char *buffer = new char[bufferSize + 1];
    int bytes, read_bytes = 0;
    
    if (!audioStream.is_open())
    {
      cerr << "Audio file does not exist!" << endl;
      exit(-1);
    }
    else
    {
      //Get file size
      audioStream.seekg(0, ios::end);
      bytes = audioStream.tellg();
    }
    //Intialize bytes to read
    int bytes_to_read = min(bufferSize, bytes - read_bytes);
    //Reset pointer to beginning for the file
    audioStream.seekg(0, ios::beg);

    //Thread to write chuncks to Gnani ASR
    //Generator function that sends chunks with a small delay
    thread writer([&]() {
      while (audioStream && bytes_to_read)
      {
        audioStream.read(buffer, bytes_to_read);
        buffer[bytes_to_read] = '\0';
        string str(buffer, bytes_to_read);
        SpeechChunk chunk;
        chunk.set_content(str);
        //Adding delay of 0.04s between chunks
        this_thread::sleep_for(chrono::milliseconds(40));
        if (!stream->Write(chunk))
          break;

        read_bytes += bytes_to_read;
        bytes_to_read = min(bufferSize, bytes - read_bytes);
      }
      //Completed writing all stream data to Gnani ASR
      stream->WritesDone();
    });

    TranscriptChunk trans;
    //Read the transcriptions for the chunks sent to Gnani ASR
    while (stream->Read(&trans)){
      cout << trans.transcript() << '\n';
    }
    //Join the writer thread once completed    
    writer.join();
    delete[] buffer;
    //Close the audio file
    audioStream.close();

    Status stat = stream->Finish();
    if (!stat.ok())
    {
      cout << stat.error_code() << " : " << stat.error_message() << endl;
      cout << "RouteChat rpc failed." << '\n';
    }
  }

private:
  unique_ptr<Listener::Stub> stub_;

  /** Function to set header - server responds only if this header is available*/
  bool setContext(ClientContext& context){
    context.AddMetadata("token", client_config.token);
    context.AddMetadata("accesskey", client_config.access_key);
    context.AddMetadata("lang", client_config.language);
    context.AddMetadata("audioformat", client_config.audio_format);
    context.AddMetadata("encoding", client_config.endoding);
  }
};

int main(int argc, char **argv)
{
  if (grpc_client_init())
  {
    if (argc != 2)
      cout << "Invalid number of arguments : Required : <audioStream file audioFilePath>/<audioStream file name>";
    string audioFilePath = argv[1];
    
    auto channel_creds = grpc::SslCredentials(grpc::SslCredentialsOptions());
    stt_client cli(grpc::CreateChannel(client_config.server_url, channel_creds));
    cli.DoSpeechToText(audioFilePath, BUFSIZE);
  }
  else
  {
    cerr << "user.config file missing!!" << endl;
  }
  return 0;
}
