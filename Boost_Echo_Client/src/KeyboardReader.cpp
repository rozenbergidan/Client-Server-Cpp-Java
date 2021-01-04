//
// Created by 97254 on 03/01/2021.
//
#include <iostream>
#include "../include/KeyboardReader.h"
#include <mutex>
#include <string>
#include <queue>
#include "../include/ConnectionHandler.h"

KeyboardReader::KeyboardReader(std::mutex & _mutex, queue<std::string> & queue): mutex(_mutex), messageQueue(queue) {}

void KeyboardReader::run() {
    while(true) {
        const short bufsize = 1024;
        char buf[bufsize];
        std::cin.getline(buf, bufsize);
        std::string line(buf);
        std::lock_guard<std::mutex> lock(mutex);
        messageQueue.push(line);
        std::lock_guard<std::mutex> unlock(mutex);
    }
}
