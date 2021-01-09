//
// Created by spl211 on 06/01/2021.
//


#include "../include/Receiver.h"

Receiver::Receiver(std::mutex & _mutex, ConnectionHandler &_connectionHandler, bool & _shouldTerminate, bool & _msgReceived): mutex(_mutex), connectionHandler(_connectionHandler), shouldTerminate(_shouldTerminate), msgReceived(_msgReceived){}

void Receiver::run(){
    while(!shouldTerminate) {
        // Get back an answer: by using the expected number of bytes (len bytes + newline delimiter)
        // We could also use: connectionHandler.getline(answer) and then get the answer without the newline char at the end

        char opCodeArr[2];
        if(!connectionHandler.getBytes(opCodeArr,2)){
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            shouldTerminate=true;
        }
        short opCode = bytesToShort(opCodeArr);
        char receivedAboutArr[2];
        if(!connectionHandler.getBytes(receivedAboutArr,2)){
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            shouldTerminate=true;

        }
        short receivedAboutOpCode = bytesToShort(receivedAboutArr);
        if(opCode == 12 ) {
            if(receivedAboutOpCode==4){
                shouldTerminate=true;
            }
            std::string message;
            if (!connectionHandler.getLine(message)) {
                std::cout << "Disconnected. Exiting...\n" << std::endl;
                shouldTerminate=true;
            }
            std::cout << "ACK " << std::to_string(receivedAboutOpCode) << " " << message << std::endl;
        }
        else if(opCode == 13){
            std::cout<< "ERR " << std::to_string(receivedAboutOpCode) << std::endl;
        }
        else{
            std::cout<<"something went wrong... opCode is not error nor ack";
        }
        msgReceived=true;
    }
}


short Receiver::bytesToShort(char* bytesArr)
{
    short result = (short)((bytesArr[0] & 0xff) << 8);
    result += (short)(bytesArr[1] & 0xff);
    return result;
}