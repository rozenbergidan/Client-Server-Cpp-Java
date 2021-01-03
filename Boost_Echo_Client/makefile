CFLAGS:= -Wall -Weffc++ -c -std=c++11 -Iinclude -lpthread -lboost_thread
LDFLAGS:=-lboost_system -lpthread -lboost_thread

all: BGRSClient
	g++ -o bin/ConnectionHandler.o bin/BGRSClient.o bin/KeyboardReader.o bin/SocketRW.o bin/echoClient.o $(LDFLAGS)
	
bin/ConnectionHandler.o: src/ConnectionHandler.cpp
	g++ $(CFLAGS) -o bin/BGRSClient.o src/ConnectionHandler.cpp

bin/BGRSClient.o: src/BGRSClient.cpp
	g++ $(CFLAGS) -o bin/BGRSClient.o src/BGRSClient.cpp

bin/KeyboardReader.o: src/KeyboardReader.cpp
	g++ $(CFLAGS) -o bin/KeyboardReader.o src/KeyboardReader.cpp

bin/SocketRW.o: src/SocketRW.cpp
	g++ $(CFLAGS) -o bin/SocketRW.o src/SocketRW.cpp


.PHONY: clean
clean:
	rm -f bin/*
