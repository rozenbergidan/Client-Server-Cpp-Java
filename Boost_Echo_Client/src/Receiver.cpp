//
// Created by spl211 on 06/01/2021.
//


#include "../include/Receiver.h"

Receiver::Receiver(std::mutex & _mutex, ConnectionHandler &_connectionHandler): mutex(_mutex), connectionHandler(_connectionHandler){}

void Receiver::run(){
    while(true) {
        // Get back an answer: by using the expected number of bytes (len bytes + newline delimiter)
        // We could also use: connectionHandler.getline(answer) and then get the answer without the newline char at the end

        char opCodeArr[2];
        if(!connectionHandler.getBytes(opCodeArr,2)){
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            break;
        }
        short opCode = bytesToShort(opCodeArr);
        char receivedAboutArr[2];
        if(!connectionHandler.getBytes(receivedAboutArr,2)){
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            break;
        }
        short receivedAboutOpCode = bytesToShort(receivedAboutArr);
        if(opCode == 12 ) {
//            if (((receivedAboutOpCode == 7) || (receivedAboutOpCode == 8) || (receivedAboutOpCode == 9) || (receivedAboutOpCode == 11))) {
                std::string message;
                if (!connectionHandler.getLine(message)) {
                    std::cout << "Disconnected. Exiting...\n" << std::endl;
                    break;
                }
                std::cout << "ACK " << std::to_string(receivedAboutOpCode) << " " << message << std::endl;
            }
//                else {
//                std::cout << "ACK " << std::to_string(receivedAboutOpCode) << std::endl;
//            }
//        }
        else if(opCode == 13){
            std::cout<< "ERR " << std::to_string(receivedAboutOpCode) << std::endl;
        }
        else{
            std::cout<<"something went wrong... opCode is not error nor ack";
        }


//        int len = answer.length();
//        // A C string must end with a 0 char delimiter.  When we filled the answer buffer from the socket
//        // we filled up to the \n char - we must make sure now that a 0 char is also present. So we truncate last character.
//        answer.resize(len - 1);
//        std::cout << "Reply: " << answer << " " << len << " bytes " << std::endl << std::endl;
//        if (answer == "bye") {
//            std::cout << "Exiting...\n" << std::endl;
//            break;
//        }
    }
}

//bool Receiver::getFrameAscii(std::string& frame, char delimiter) {
//    char ch;
//    // Stop when we encounter the null character.
//    // Notice that the null character is not appended to the frame string.
//    try {
//        do{
//            if(!connectionHandler.getBytes(&ch, 1))
//            {
//                return false;
//            }
//            if(ch!='\0')
//                frame.append(1, ch);
//        }while (delimiter != ch);
//    } catch (std::exception& e) {
//        std::cerr << "recv failed2 (Error: " << e.what() << ')' << std::endl;
//        return false;
//    }
//    return true;
//}

short Receiver::bytesToShort(char* bytesArr)
{
    short result = (short)((bytesArr[0] & 0xff) << 8);
    result += (short)(bytesArr[1] & 0xff);
    return result;
}