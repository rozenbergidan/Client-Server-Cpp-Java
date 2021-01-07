//
// Created by spl211 on 05/01/2021.
//

#include <iostream>

#include "../include/InputHandler.h"
#include <exception>


//InputHendler::InputHendler(std::mutex & _mutex, std::queue<char[]> & queue): mutex(_mutex), messageQueue(queue) {}
InputHandler::InputHandler(std::mutex & _mutex, ConnectionHandler &_connectionHandler): mutex(_mutex), connectionHandler(_connectionHandler){}

void InputHandler::run(){
    while(true) {
        const short bufsize = 1024;
        char buf[bufsize];
        std::cin.getline(buf, bufsize);
        std::string line(buf);
        try{
            verifyValidInput(line);//verify if the input is legal, throw exception if necessary.

            std::string operation = line.substr(0,line.find(" "));
            char opArr[2];
            operationToCharArr(operation,opArr);//opArr holds the opCode in bytes.

            std::string restOfTheLine= line.substr(line.find(" ")+1);

            int restArrLen = getRestArrSize(operation,restOfTheLine); // predict how much bytes we will need.
            char restArr [restArrLen]; // create array in the right size.
            opToFullMessage(operation,restOfTheLine,restArr);// fill the array with values.

            char send[2 + restArrLen]; //create the output array
            send[0] = opArr[0];
            send[1] = opArr[1];
            for(int i = 2; i < 2 + restArrLen; i++){
                send[i] = restArr[i-2];
            }
            if (!connectionHandler.sendBytes(send, 2 + restArrLen)) {
                std::cout << "Disconnected. Exiting...\n" << std::endl;
                break;
            }
            std::cout << "Sent " << 2 + restArrLen << " bytes to server" << std::endl;
        }catch (std::exception &e) {
            std::cout<<"invalid input"<<std::endl;
        }

    }
}

void InputHandler::shortToBytes(short num, char* bytesArr)
{
    bytesArr[0] = ((num >> 8) & 0xFF);
    bytesArr[1] = (num & 0xFF);
}

void InputHandler::operationToCharArr(std::string operation, char *output){
    if(operation.compare("ADMINREG")==0){
        shortToBytes(1,output);
    }
    else if(operation.compare("STUDENTREG")==0){
        shortToBytes(2,output);
    }
    else if(operation.compare("LOGIN")==0){
        shortToBytes(3,output);
    }
    else if(operation.compare("LOGOUT")==0){
        shortToBytes(4,output);
    }
    else if(operation.compare("COURSEREG")==0){
        shortToBytes(5,output);
    }
    else if(operation.compare("KDAMCHECK")==0){
        shortToBytes(6,output);
    }
    else if(operation.compare("COURSESTAT")==0){
        shortToBytes(7,output);
    }
    else if(operation.compare("STUDENTSTAT")==0){
        shortToBytes(8,output);
    }
    else if(operation.compare("ISREGISTERED")==0){
        shortToBytes(9,output);
    }
    else if(operation.compare("UNREGISTER")==0){
        shortToBytes(10,output);
    }
    else if(operation.compare("MYCOURSES")==0){
        shortToBytes(11,output);
    }
}

void InputHandler::opToFullMessage(std::string opCode, std::string line, char *output) {
    if(opCode.compare("ADMINREG") == 0){
        stringToCharArr(line,output);
    }
    else if(opCode.compare("STUDENTREG") == 0){
        stringToCharArr(line,output);
    }
    else if(opCode.compare("LOGIN") == 0){
        stringToCharArr(line,output);
    }
    else if(opCode.compare("LOGOUT") == 0){
    }
    else if(opCode.compare("COURSEREG") == 0){
        shortToBytes(stringToShort(line),output);
    }
    else if(opCode.compare("KDAMCHECK") == 0){
        shortToBytes(stringToShort(line),output);
    }
    else if(opCode.compare("COURSESTAT") == 0){
        shortToBytes(stringToShort(line),output);
    }
    else if(opCode.compare("STUDENTSTAT") == 0){
        stringToCharArr(line,output);
    }
    else if(opCode.compare("ISREGISTERED") == 0){
        shortToBytes(stringToShort(line),output);
    }
    else if(opCode.compare("UNREGISTER") == 0){
        shortToBytes(stringToShort(line),output);
    }
    else if(opCode.compare("MYCOURSES") == 0){
    }
    else throw std::exception();
}

void InputHandler::stringToCharArr(std::string line, char output[]) {//if you use this fuction it is up to you to make sure that output.length is line.length + 1
    const char* lineBytes= line.c_str();
    int length= line.length();
    for(int i = 0; i < length; i++){
        if(lineBytes[i] != ' '){
            output[i] = lineBytes[i];
        }
        else{
            output[i] = '\0';
        }
    }
    output[line.length()]='\0';
}

int InputHandler::getRestArrSize(std::string operation, std::string restOfTheLine) {
    if(operation.compare("ADMINREG")==0){
        return restOfTheLine.length() + 1;
    }
    else if(operation.compare("STUDENTREG")==0){
        return  restOfTheLine.length() + 1;
    }
    else if(operation.compare("LOGIN")==0){
        return  restOfTheLine.length() + 1;
    }
    else if(operation.compare("LOGOUT")==0){
        return 0;
    }
    else if(operation.compare("COURSEREG")==0){
        return 2;
    }
    else if(operation.compare("KDAMCHECK")==0){
        return 2;
    }
    else if(operation.compare("COURSESTAT")==0){
        return 2;
    }
    else if(operation.compare("STUDENTSTAT")==0){
        return restOfTheLine.length() + 1;
    }
    else if(operation.compare("ISREGISTERED")==0){
        return 2;
    }
    else if(operation.compare("UNREGISTER")==0){
        return 2;
    }
    else if(operation.compare("MYCOURSES")==0){
        return 0;
    }

    return 40;
}

short InputHandler::stringToShort(std::string numAsString) {// if you use this function it is up to ypu to use try, catch and print to the user the message.
    short p = 1;
    short output = 0;
    for ( int i = numAsString.length()-1; i >= 0; i--){
        if(numAsString[i]>'9' || numAsString[i]<'0') throw std::exception();
        output = output + ((short)numAsString[i] - (short)'0')*p;
        p=p*10;
    }
    return output;
}

void InputHandler::verifyValidInput(std::string &input) {
    int spaceCount = 0;
    int length=input.length();
    for(int i = 0; i < length; i++){
        if(!(input[i]<='9' && input[i]>='0') && !(input[i]<='Z' && input[i]>='A') && !(input[i]<='z' && input[i]>='a') && input[i]!=' ') throw std::exception();
        if(i != 0){
            if(input[i-1] == ' ' && input[i] == ' ' ) throw std::exception();
        }
        if(input[i] == ' '){
            spaceCount++;
            if(spaceCount > 2) throw std::exception();
            if(i == 0) throw std::exception();
        }
    }

}