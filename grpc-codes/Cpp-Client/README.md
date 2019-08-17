# GRPC CPP STREAM CLIENT

## INSTALLATION STEPS:

sudo apt-get install build-essential autoconf libtool pkg-config

sudo apt-get install libgflags-dev libgtest-dev

sudo apt-get install clang libc++-dev

git clone -b $(curl -L https://grpc.io/release) https://github.com/grpc/grpc

cd grpc

git submodule update --init

make

sudo make install

cd third_party/protobuf

make 

sudo make install

git clone https://github.com/gnani-ai/API-service.git

cd API-service/grpc-codes/Cpp-Client

sh build.sh


## USER CONFIGURATION STEPS:

In the user.config file check if the following details are updated.

API_URL=asr.gnani.ai:443

TOKEN=<token provided by gnani.ai>

ACCESSKEY=<access key provided by gnani.ai>

ENCODING=pcm16

LANGUAGE_CODE=eng_IN 

AUDIOFORMAT=wav


## EXECUTION:

./client audio/english.wav
