//
// Created by 97254 on 03/01/2021.
//


#include "../include/SocketRW.h"
#include <mutex>
#include <string>
#include <queue>
#include <iostream>
#include "../include/ConnectionHandler.h"

SocketRW::SocketRW(std::mutex &_mutex, queue<std::string> & _queue, std::string _host, int _port):mutex(_mutex),messageQueue(_queue),connectionHandler(_host, _port) {}

void SocketRW::run() {
    while(1){
        std::string line;
        if(!messageQueue.empty()) {
            std::lock_guard <std::mutex> lock(mutex);
            line = messageQueue.front();
            messageQueue.pop();
            std::lock_guard <std::mutex> unlock(mutex);
            int len=line.length();
            if (!connectionHandler.sendLine(line)) {
                cout << "Disconnected. Exiting...\n" << std::endl;
                break;
            }
            // connectionHandler.sendLine(line) appends '\n' to the message. Therefor we send len+1 bytes.
            std::cout << "Sent " << len+1 << " bytes to server" << std::endl;
        }
        // We can use one of three options to read data from the server:
        // 1. Read a fixed number of characters
        // 2. Read a line (up to the newline character using the getline() buffered reader
        // 3. Read up to the null character
        std::string answer;
        // Get back an answer: by using the expected number of bytes (len bytes + newline delimiter)
        // We could also use: connectionHandler.getline(answer) and then get the answer without the newline char at the end
        if (!connectionHandler.getLine(answer)) {
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            break;
        }

        int len=answer.length();
        // A C string must end with a 0 char delimiter.  When we filled the answer buffer from the socket
        // we filled up to the \n char - we must make sure now that a 0 char is also present. So we truncate last character.
        answer.resize(len-1);
        std::cout << "Reply: " << answer << " " << len << " bytes " << std::endl << std::endl;
        if (answer == "bye") {
            std::cout << "Exiting...\n" << std::endl;
            break;
        }
    }
}


