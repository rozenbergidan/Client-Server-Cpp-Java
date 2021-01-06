#include "../include/ConnectionHandler.h"
#include <string>
#include <iostream>
#include <boost/asio.hpp>

using boost::asio::ip::tcp;

using std::cin;
using std::cout;
using std::cerr;
using std::endl;
using std::string;

ConnectionHandler::ConnectionHandler(string host, short port): host_(host), port_(port), io_service_(), socket_(io_service_){}

ConnectionHandler::~ConnectionHandler() {
    close();
}
bool ConnectionHandler::connect() {
    std::cout << "Starting connect to "
              << host_ << ":" << port_ << std::endl;
    try {
        tcp::endpoint endpoint(boost::asio::ip::address::from_string(host_), port_); // the server endpoint
        boost::system::error_code error;
        socket_.connect(endpoint, error);
        if (error)
            throw boost::system::system_error(error);
    }
    catch (std::exception& e) {
        std::cerr << "Connection failed (Error: " << e.what() << ')' << std::endl;
        return false;
    }
    return true;
}

bool ConnectionHandler::getBytes(char bytes[], unsigned int bytesToRead) {
    size_t tmp = 0;
    boost::system::error_code error;
    try {
        while (!error && bytesToRead > tmp ) {
            tmp += socket_.read_some(boost::asio::buffer(bytes+tmp, bytesToRead-tmp), error);
        }
        if(error)
            throw boost::system::system_error(error);
    } catch (std::exception& e) {
        std::cerr << "recv failed (Error: " << e.what() << ')' << std::endl;
        return false;
    }
    return true;
}

bool ConnectionHandler::sendBytes(const char bytes[], int bytesToWrite) {
    int tmp = 0;
    boost::system::error_code error;
    try {
        while (!error && bytesToWrite > tmp ) {
            tmp += socket_.write_some(boost::asio::buffer(bytes + tmp, bytesToWrite - tmp), error);
        }
        if(error)
            throw boost::system::system_error(error);
    } catch (std::exception& e) {
        std::cerr << "recv failed (Error: " << e.what() << ')' << std::endl;
        return false;
    }
    return true;
}

bool ConnectionHandler::getLine(std::string& line) {
    return getFrameAscii(line, '\n');
}


bool ConnectionHandler::sendLine(std::string& line) {
    return sendFrameAscii(line, '\n');
    // get
}




char* ConnectionHandler::twoStringBytes(std::string& str){

//    std::string temp=str.data();
//    int space1=temp.find(" ");
//    temp=temp.substr(space1);
//    int space2=temp.find(" ");
//    std::string username=temp.substr(0,space2);
//    temp=temp.substr(space2);
//    std::string password=temp;
//
//    const char* userNameBytes= username.c_str();
//    const char* passwordBytes=password.c_str();
//    char* output[userNameBytes.length()+passwordBytes.length()+2];
//    for(int i=0;i<userNameBytes.length();i++){
//        output[i]=userNameBytes[i];
//    }
//    output[userNameBytes]='\0';
//    for(int i=0;i<passwordBytes.length();i++){
//        output[i+userNameBytes.length()+1]=password[i];
//    }
//    output[output.length()-1]='\0';
//    return output;

}

void ConnectionHandler::shortToBytes(short num, char* bytesArr)
{
    bytesArr[0] = ((num >> 8) & 0xFF);
    bytesArr[1] = (num & 0xFF);
}

short ConnectionHandler::bytesToShort(char* bytesArr)
{
    short result = (short)((bytesArr[0] & 0xff) << 8);
    result += (short)(bytesArr[1] & 0xff);
    return result;
}


void ConnectionHandler::ConvertToBytes(std::string& str){
    int cnt=str.find(" ");
//    if(cnt==-1){
//        return nullptr;
//    }

    std::string operation = str.substr(0, cnt);

    char opCodeBytes[str.size()];

     if(operation.compare("ADMINREG")==0){
        shortToBytes(1,opCodeBytes);
        string  s = "";
        s= opCodeBytes[0] + opCodeBytes[1];
        cout<< s;
        //twoStringBytes(str);
        const char * arr = str.c_str();
        int i = 0;
        while(*(arr + i) != '\0'){
            if(*(arr + i) == ' '){

            }
        }
    }
    else if(operation.compare("STUNDETREG")==0){
        shortToBytes(2,opCodeBytes);
        twoStringBytes(str);
        for (int i = 0; i < str.length();i= i + 1){

        }
    }
    else if(operation.compare("LOGIN")==0){
        shortToBytes(3,opCodeBytes);
        twoStringBytes(str);
    }
    else if(operation.compare("LOGOUT")==0){
        shortToBytes(4,opCodeBytes);
    }
    else if(operation.compare("COURSEREG")==0){
        shortToBytes(5,opCodeBytes);
    }
    else if(operation.compare("KDAMCHECK")==0){
        shortToBytes(6,opCodeBytes);
    }
    else if(operation.compare("COURSESTAT")==0){
        shortToBytes(7,opCodeBytes);
    }
    else if(operation.compare("STUDENTSTAT")==0){
        shortToBytes(8,opCodeBytes);
    }
    else if(operation.compare("ISREGISTERED")==0){
        shortToBytes(9,opCodeBytes);
    }
    else if(operation.compare("UNREGISTER")==0){
        shortToBytes(10,opCodeBytes);
    }
    else if(operation.compare("MYCOURSES")==0){
        shortToBytes(11,opCodeBytes);
    }

    

}

bool ConnectionHandler::getFrameAscii(std::string& frame, char delimiter) {
    char ch;
    // Stop when we encounter the null character.
    // Notice that the null character is not appended to the frame string.
    try {
        do{
            if(!getBytes(&ch, 1))
            {
                return false;
            }
            if(ch!='\0')
                frame.append(1, ch);
        }while (delimiter != ch);
    } catch (std::exception& e) {
        std::cerr << "recv failed2 (Error: " << e.what() << ')' << std::endl;
        return false;
    }
    return true;
}


bool ConnectionHandler::sendFrameAscii(const std::string& frame, char delimiter) {
    bool result=sendBytes(frame.c_str(),frame.length());
    if(!result) return false;
    return sendBytes(&delimiter,1);
}


// Close down the connection properly.
void ConnectionHandler::close() {
    try{
        socket_.close();
    } catch (...) {
        std::cout << "closing failed: connection already closed" << std::endl;
    }
}
