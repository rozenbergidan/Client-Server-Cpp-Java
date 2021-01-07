CFLAGS:= -Wall -Weffc++ -c -std=c++11 -Iinclude -lpthread -lboost_thread
LDFLAGS:=-lboost_system -lpthread -lboost_thread

all: BGRSClient
	g++ -o bin/BGRSClient bin/ConnectionHandler.o bin/BGRSClient.o bin/InputHandler.o bin/Receiver.o $(LDFLAGS)

BGRSClient: bin/ConnectionHandler.o bin/BGRSClient.o bin/InputHandler.o bin/Receiver.o

bin/BGRSClient.o: src/BGRSClient.cpp
	g++ $(CFLAGS) -o bin/BGRSClient.o src/BGRSClient.cpp

bin/InputHandler.o: src/InputHandler.cpp
	g++ $(CFLAGS) -o bin/InputHandler.o src/InputHandler.cpp

bin/Receiver.o: src/Receiver.cpp
	g++ $(CFLAGS) -o bin/Receiver.o src/Receiver.cpp

bin/ConnectionHandler.o: src/ConnectionHandler.cpp
	g++ $(CFLAGS) -o bin/ConnectionHandler.o src/ConnectionHandler.cpp

.PHONY: clean
clean:
	rm -f bin/*
