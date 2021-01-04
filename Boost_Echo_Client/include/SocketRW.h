//
// Created by 97254 on 03/01/2021.
//

#ifndef BOOST_ECHO_CLIENT_SOCKETRW_H
#define BOOST_ECHO_CLIENT_SOCKETRW_H
#include "../include/ConnectionHandler.h"
#include <mutex>
#include <queue>
#include <iostream>
using namespace std;

class SocketRW {
public:
    std::mutex & mutex;
    queue<std::string> & messageQueue;
    ConnectionHandler connectionHandler;

    SocketRW(std::mutex & _mutex , queue<std::string> & queue,std::string _host, int _port );
    void run();
};


#endif //BOOST_ECHO_CLIENT_SOCKETRW_H
