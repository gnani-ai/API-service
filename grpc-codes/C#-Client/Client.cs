using System;
using Grpc.Core;
using Helloworld;
using System.Configuration;


private void SetUpGrpc()
        {
            try
            {
                string language = ConfigurationManager.AppSettings["LANGUAGE_CODE"]; //Read language here;
                string token =  ConfigurationManager.AppSettings["TOKEN"];
                string accesskey = ConfigurationManager.AppSettings["ACCESSKEY"];
                string URL = ConfigurationManager.AppSettings["API_URL"];
                string audioformat = ConfigurationManager.AppSettings["AUDIOFORMAT"];
                string encoding = ConfigurationManager.AppSettings["ENCODING"];
                var assembly = Assembly.GetExecutingAssembly();
                var resourceName = assembly.GetManifestResourceNames().Single(str => str.EndsWith("cert.pem")); //Read certificate file 
                using Stream stream = assembly.GetManifestResourceStream(resourceName);
                using StreamReader reader = new StreamReader(stream);
                string result = reader.ReadToEnd();
                var channelCredentials = new SslCredentials(result);
                channel = new grpc::Channel(URL, channelCredentials);
                client = new Listener.ListenerClient(channel);
                var metadata = new Grpc.Core.Metadata();


                metadata.Add("token", token);
                metadata.Add("lang", language);
                metadata.Add("accesskey", accesskey);
                metadata.Add("audioformat", encoding);
                metadata.Add("encoding", encoding);
                metadata.Add("sensitive", "Y");
                metadata.Add('filename', audioFilePath);
                var call = client.DoSpeechToText(metadata);
                req = call.RequestStream; //stream is being sent here 
                res = call.ResponseStream;  //You will recieve data here 
            }

            catch { 
                // Catch error here;
            }
        }
