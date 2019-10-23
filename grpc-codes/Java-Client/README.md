## Grpc-Java-Client
Grpc-Java Client is a Speech to text module


## Installation
Code tested with java version 8 (Ubuntu 18.04 latest LTS version) and Apache Maven 3.6.0

# Ubuntu 16.04/18.04 LTS

# Prerequisites
##### Java version 8 : In terminal :
    

 * Go to grpc-java-client folder and run command for permission : `chmod +x install-Java8.sh` 
        
 * Run command `./install-Java8.sh`
        
 * `java -version` - Check version
 

##### Maven installation : In terminal :

 * Go to grpc-java-client folder and run command `chmod +x install-Maven.sh` : permission
        
 * Run command `./install-Maven.sh`
        
 *  `mvn -version` - Check version


# Steps to run the Java-Client
*  Update the Credentials [Token, Language code and Access Key] in src/main/resources/config.properties file
*  Update the certificate in src/main/resources/cert.pem file
*  Go to grpc-java-client folder and then run the following commands:

     * `mvn`

     * `java -jar target/javaclient-0.0.1-SNAPSHOT.jar`

#
# Mac OSX

# Prerequisites

##### Java version 8 

*  `brew cask install java`

##### Maven installation 

*  `brew install maven`

# Steps to run the Java-Client

*  Update the Credentials [Token, Language code and Access Key] in src/main/resources/config.properties file
*  Update the certificate in src/main/resources/cert.pem file
*  Go to grpc-java-client folder and then run the following commands:

    * `mvn`
    
    * `java -jar target/javaclient-0.0.1-SNAPSHOT.jar`


#
# Windows 8/8.1/10

# Prerequisites

*  Java version 8 && Maven installation

    
   *   Go to the official Oracle site for Java 8 download : https://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html and install Windowsx64 
   *   Run the downloaded "jdk-8u221-windows-x64" Installer - including accepting the license, selecting the destination, and authenticating for the install. This requires Administrator
       privileges, and you may need to authenticate

    
   *  Go to the official Apache site for maven download : http://maven.apache.org/download.cgi download the Maven zip file [Binary zip Archive], and unzip the file in a new folder 
      named opt under C drive - c:\opt\apache-maven-3.6.0.


    *  Finally, run the bash script as administrator `set_windows_env.bat`




# Steps to run the Java-Client
*  Update the Credentials [Token, Language code and Access Key] in src/main/resources/config.properties file
*  Update the certificate in src/main/resources/cert.pem file
*  Go to grpc-java-client folder then run the following commands:

    * `mvn`
    
    * `java -jar target/javaclient-0.0.1-SNAPSHOT.jar`









______________________________________________________________________
### Note
* For any issues please check our [Error codes](https://github.com/gnani-ai/API-service).