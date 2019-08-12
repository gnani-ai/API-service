protoc -I proto --grpc_out=. --plugin=protoc-gen-grpc=`which grpc_cpp_plugin` proto/stt.proto 
protoc -I proto --cpp_out=. proto/stt.proto

g++ -g -std=c++11 `pkg-config --cflags protobuf grpc`  -c -o client.o client.cc
g++ -g -std=c++11 `pkg-config --cflags protobuf grpc`  -c -o stt.pb.o stt.pb.cc
g++ -g -std=c++11 `pkg-config --cflags protobuf grpc`  -c -o stt.grpc.pb.o stt.grpc.pb.cc 
g++ -g  stt.pb.o stt.grpc.pb.o client.o -L/usr/local/lib `pkg-config --libs protobuf grpc++ grpc` -Wl,--no-as-needed -lgrpc++_reflection -Wl,--as-needed -ldl -o client

   
  
