//
// Created by 97254 on 03/01/2021.
//

#ifndef BOOST_ECHO_CLIENT_SOCKERRW_H
#define BOOST_ECHO_CLIENT_SOCKERRW_H


class SocketRW {
public:
    std::mutex & mutex;
    queue<std::string> & messageQueue;
    ConnectionHandler connectionHandler;

    SocketRW(std::mutex & _mutex , queue<std::string> & queue,std::string host, int port );
    void run();
};


#endif //BOOST_ECHO_CLIENT_SOCKERRW_H
