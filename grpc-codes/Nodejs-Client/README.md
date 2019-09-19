# Grpc nodejs stream client

## Installation

Code tested with nodejs version 10.x (latest LTS version) and npm version 6.x

### Ubuntu 16.04/18.04
- `sudo apt-get install curl`
- `curl -sL https://deb.nodesource.com/setup_10.x`
- `sudo apt-get install nodejs`
- `node -v` 
- `npm -v`

- After installing nodejs and npm go to the directory : `cd grpc-nodejs-client/` and install the dependencies : `npm install`
- Update configurations in config/config.js file 
- Update the certificate file in config/ folder
- `node client.js`

### Mac OSX
- `brew install node`
- `node -v`
- `npm -v`

- After installing nodejs and npm go to the directory : `cd grpc-nodejs-client/` and install the dependencies : `npm install`
- Update configurations in config/config.js file 
- Update the certificate file in config/ folder
- `node client.js`

### Windows 8/8.1/10

Here's the abbreviated guide, highlighting the major steps:


- Open the official page for [Node.js downloads](https://nodejs.org/en/download/) and download Node.js for Windows by clicking the "Windows Installer" option
- Run the downloaded Node.js .msi Installer - including accepting the license, selecting the destination, and authenticating for the install.
This requires Administrator privileges, and you may need to authenticate
- To ensure Node.js has been installed, run node -v in your terminal - you should get something like v10.3.5
- `node -v`
- Update your version of npm with npm install npm --global
- `sudo npm install npm --global`
- `npm -v`

- After installing nodejs and npm go to the directory : `cd grpc-nodejs-client/` and install the dependencies : `npm install`
- Update configurations in config/config.js file 
- Update the certificate file in config/ folder
- `node client.js`

___


### Note
* For any issues please check our [Error codes](https://github.com/gnani-ai/API-service).
