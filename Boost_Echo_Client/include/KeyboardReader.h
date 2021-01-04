//
// Created by 97254 on 03/01/2021.
//

#ifndef BOOST_ECHO_CLIENT_KEYBOARDREADER_H
#define BOOST_ECHO_CLIENT_KEYBOARDREADER_H

using namespace std;

class KeyboardReader {
public:
    std::mutex & mutex;
    queue<std::string> & messageQueue;
    KeyboardReader(std::mutex & _mutex , queue<std::string> & queue);
    void run();
};


#endif //BOOST_ECHO_CLIENT_KEYBOARDREADER_H
