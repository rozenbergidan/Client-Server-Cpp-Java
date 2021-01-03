//
// Created by 97254 on 01/01/2021.
//

#ifndef BOOST_ECHO_CLIENT_BGRSCLIENT_H
#define BOOST_ECHO_CLIENT_BGRSCLIENT_H


class BGRSClient {
public:
    public BGRSClient();
    public bool openConnection(std::string serverIP, short port);
    public void run();

};


#endif //BOOST_ECHO_CLIENT_BGRSCLIENT_H
