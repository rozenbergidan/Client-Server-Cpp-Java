//
// Created by spl211 on 06/01/2021.
//

#ifndef BOOST_ECHO_CLIENT_RECEIVER_H
#define BOOST_ECHO_CLIENT_RECEIVER_H

#include <mutex>
#include "ConnectionHandler.h"


class Receiver {
public:
    Receiver(std::mutex & _mutex, ConnectionHandler &_connectionHandler);
    void run( );

private:

    std::mutex & mutex;
    ConnectionHandler &connectionHandler;

    bool getFrameAscii(std::string& frame, char delimiter);

    short bytesToShort(char *bytesArr);
};


#endif //BOOST_ECHO_CLIENT_RECEIVER_H
