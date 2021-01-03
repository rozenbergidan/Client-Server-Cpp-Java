//
// Created by 97254 on 03/01/2021.
//

#ifndef BOOST_ECHO_CLIENT_TASK_H
#define BOOST_ECHO_CLIENT_TASK_H
#include <mutex>
#include <string>
#include <queue>
using namespace std;

class KeyboardReader {
public:
    std::mutex & mutex;
    queue<std::string> messageQueue;
    KeyboardReader(std::mutex & _mutex);
    void run();
};


#endif //BOOST_ECHO_CLIENT_TASK_H
