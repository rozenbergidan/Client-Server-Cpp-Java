#include <stdlib.h>
#include "../include/ConnectionHandler.h"
#include "../include/InputHandler.h"
#include "../include/Receiver.h"
#include <mutex>
#include <iostream>
#include <thread>

/**
* This code assumes that the server replies the exact text the client sent it (as opposed to the practical session example)
*/

int main (int argc, char *argv[]) {


    if (argc < 3) {
        std::cerr << "Usage: " << argv[0] << " host port" << std::endl << std::endl;
        return -1;
    }
    std::string host = argv[1];
    short port = atoi(argv[2]);

    ConnectionHandler connectionHandler(host, port);
    if (!connectionHandler.connect()) {
        std::cerr << "Cannot connect to " << host << ":" << port << std::endl;
        return 1;
    }

    std::mutex mutex;
    bool shouldTerminate=false;
    bool msgReceived = true;
    std::lock_guard<std::mutex> lock(mutex);
    Receiver receiver(mutex,connectionHandler,shouldTerminate, msgReceived);
    InputHandler inputHandler(mutex,connectionHandler,shouldTerminate,msgReceived);

    std::thread inputThread(&InputHandler::run, &inputHandler);
    receiver.run();

    inputThread.join();

    return 0;
}