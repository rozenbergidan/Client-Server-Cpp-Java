//
// Created by 97254 on 03/01/2021.
//
#include <iostream>
#include "../include/KeyboardReader.h"



KeyboardReader::KeyboardReader(std::mutex & _mutex, queue<std::string> queue):mutex(_mutex), messageQueue(queue) {}

std::string KeyboardReader::run() {
    while(true) {
        const short bufsize = 1024;
        char buf[bufsize];
        std::cin.getline(buf, bufsize);
        std::string line(buf);
        std::lock_guard<std::mutex> lock(_mutex);
        messageQueue.pop(line);
        std::lock_guard<std::mutex> unlock(_mutex);

    }
}
