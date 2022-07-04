package com.gnani.httpclient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/*
 *  HttpsRequest Wrapper class
 *  implements:
 *      - setHeaders(...)
 *      - uploadRequest(...)  # a custom method to configure request for upload file using multipart/form
 *      - execute()
 *      - getResponseCode()
 */
public class HttpsRequest {

    private HttpsURLConnection conn;
    String attachmentName = "audio_file";
    String attachmentFileName = "english.wav";
    String crlf = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";
    boolean executed = false;

    /*
     * Create HttpsURLConnection with URL provided
     */
    public HttpsRequest(String httpsURL) throws MalformedURLException, IOException, CertificateException,
            KeyManagementException, KeyStoreException, NoSuchAlgorithmException {
        URL myUrl = new URL(httpsURL);
        conn = (HttpsURLConnection)myUrl.openConnection();

        /*
         *  Configure connection to use our pem certificate for Secure
         */
        InputStream fis = App.class.getResourceAsStream("/chain.pem");
        X509Certificate ca = (X509Certificate) CertificateFactory.getInstance(
                "X.509").generateCertificate(new BufferedInputStream(fis));

        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(null, null);
        ks.setCertificateEntry(Integer.toString(1), ca);

        TrustManagerFactory tmf = TrustManagerFactory
                .getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ks);

        SSLContext s = SSLContext.getInstance("TLS");
        s.init(null, tmf.getTrustManagers(), null);
        conn.setSSLSocketFactory(s.getSocketFactory());
       
    }

    // set Headers
    public void setHeaders (String method, String token, String accessKey, String lang, String audioFormat, String encoding, String requestid) throws ProtocolException{
        conn.setRequestMethod(method);

        conn.setRequestProperty("token", token); 
        conn.setRequestProperty("lang", lang);
        conn.setRequestProperty("accesskey", accessKey);
        conn.setRequestProperty("audioformat", audioFormat);
        conn.setRequestProperty("encoding", encoding);
        if (null != requestid){
            conn.setRequestProperty("requestid", requestid);filePath
        }
        conn.setUseCaches(false);
        conn.setDoOutput(true);
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Cache-Control", "no-cache");
        conn.setRequestProperty(
            "Content-Type", "multipart/form-data;boundary=" + boundary);
    }

    /*
     *  Configures request for uploading file.
     *      args: filePath
     */
    public void uploadRequest(String filePath) throws ProtocolException, IOException {
        
        DataOutputStream request = new DataOutputStream(
        conn.getOutputStream());

        // start content wrapper
        request.writeBytes(this.twoHyphens + this.boundary + this.crlf);
        request.writeBytes("Content-Disposition: form-data; name=\"" +
            this.attachmentName + "\";filename=\"" + 
            filePath + "\"" + this.crlf);
        request.writeBytes(this.crlf);

        // Write file content
        InputStream i = App.class.getResourceAsStream(filePath);
        byte[] file = toByteArray(i);
        request.write(file);

        // End content wrapper
        request.writeBytes(this.crlf);
        request.writeBytes(this.twoHyphens + this.boundary + filePath
            this.twoHyphens + this.crlf);



        request.flush();
        request.close();
    }

    /*
     *  Returns the responseBody as String
     *      Reads InputStream i.e. response from server.
     *      
     *      Called in execute()
     */
    private String getResponse() throws IOException {
        // get response
        InputStream responseStream ;
      
        responseStream = new BufferedInputStream(conn.getInputStream());
        BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));

        String line = "";
        StringBuilder stringBuilder = new StringBuilder();

        while ((line = responseStreamReader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        responseStreamReader.close();

        String response = stringBuilder.toString();
        
        return response;
    }

    /*
     *  Convert InputStream file to Byte[]
     *      Used for uploading file
     */
    private byte[] toByteArray(InputStream in) throws IOException {
        
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int len;

        // read bytes from the input stream and store them in buffer
        while ((len = in.read(buffer)) != -1) {
          // write bytes from the buffer into output stream
          os.write(buffer, 0, len);
        }

        return os.toByteArray();
    }

    /*
     *  Execute this request
     *      returns: Response body as String
     *  Calling execute() more than once throws IOException
     */
    public String execute() throws IOException {
        if (executed)
            throw new IOException("Request already executed, cannot execute again");
        executed = true;
        conn.getResponseCode(); 
        String res = getResponse();
        closeConnection();

        return res;
    }

    /*
     *  Returns responseCode from server.
     *  Throws exception if called before execute()
     */
    public int getResponseCode() throws IOException {
        if (executed)
            return conn.getResponseCode();
        throw new IOException("Cannot getResponseCode() before execute()");
    }

     //close connection
     private void closeConnection () {
        conn.disconnect();
    }
}
