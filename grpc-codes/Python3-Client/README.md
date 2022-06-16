# Grpc python stream client

## Note
- You must have minimum python version 3.8 installed to this project.

## Installation

### Ubuntu
- apt-get install python3.8
- apt-get install python3-pip

### Mac OSX
- `brew install python3`
- `brew install python3-pip`

___
- `pip3 install -r requirements.txt`
- update details in `user.config`
- `python3 client.py`

### Windows
- Open PowerShell console as an administrator
- Install Chocolatey package manager for windows.
		`Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))`
- Install python 3.6.1
		`choco install python --version=3.6.1 -y -f`
- Download `get-pip.py` 
- Install pip
		`python get-pip.py`
- Restart PowerShell console as administrator
- `pip install grpcio`
- `pip install grpcio_tools`

### Note
* For any issues please check our [Error codes](https://github.com/gnani-ai/API-service).
