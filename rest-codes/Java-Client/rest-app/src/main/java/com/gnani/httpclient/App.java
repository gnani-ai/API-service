package com.gnani.httpclient;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class App{
    public static void main(String[] args) throws Exception{
        Properties prop = new Properties();

        
        // Open config file and load it in Properties prop object
        // Reading from config file 
          
        prop.load(App.class.getResourceAsStream("/config.properties"));
       
        try{

            String filePath = prop.getProperty("AUDIO_FILE");
            String token = prop.getProperty("TOKEN");
            String accessKey = prop.getProperty("ACCESSKEY");
            String encoding = prop.getProperty("ENCODING");
            String lang = prop.getProperty("LANGUAGE_CODE");
            String audioFormat = prop.getProperty("AUDIOFORMAT");
            String api_upload_url = prop.getProperty("API_UPLOAD_URL");
            String api_status_url = prop.getProperty("API_STATUS_URL");
            
          

            /*
            Create an Https Request to upload file, setHeaders and setContent (Multipart:form request to upload file)
            Method uploading audio file for speech to text conversion
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
            */
            String transcriptId = "";
            HttpsRequest request = null;
            for (int retry = 0; retry < 10; retry++){
                try{
                    request = new HttpsRequest(api_upload_url);
                    request.setHeaders("POST", token, accessKey, lang, audioFormat, encoding);
                    request.uploadRequest(filePath);
    
                    // execute the request
                    transcriptId = request.execute();    
                    System.out.println("transcriptId = "+transcriptId);
                    break;
                }
                catch(IOException exe)
                {
                    if (retry == 9) {
                        System.out.println(exe.getMessage());
                        return;
                    }
                    System.out.println ("Please wait trying to rebuild the connection with server........");
                    TimeUnit.SECONDS.sleep(60);
                }
            }
            
      
            /*
            Create another HttpsRequest to getStatus and setHeaders
            Method getting the status of uploaded audio file for speech to text conversion
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
            */

            String response = "";
            for(int i = 0; i<10; i++)
            {   
                    
                    for (int retry = 0; retry < 10; retry++){
                        try {
                            request = new HttpsRequest(api_status_url+"?transcript_key="+transcriptId);
                            request.setHeaders("POST", token, accessKey, lang, audioFormat, encoding);
                            // execute the request
                            response = request.execute();
                            break;
                        } catch (IOException ex) {
                            if (retry == 9) {
                                System.out.println(ex.getMessage());
                                return;
                            }
                            System.out.println ("Please wait trying to rebuild the connection with server.......");
                            TimeUnit.SECONDS.sleep(60);
                        }
                    }
                    if(request.getResponseCode() == 200 && response.contains("Decoding in progress")){
                        System.out.println("Please wait decoding in progress...");
                        //wait time 60 sec
                        TimeUnit.SECONDS.sleep(60);
                    }
                    else
                    {
                        System.out.println();
                        System.out.println("Audio to text result in JSON format :");
                        System.out.println();
                        System.out.println(response);
                        break;
                    }    
            } 

        } catch (Exception ex) {
            ex.getMessage();
        }      
    }
}