cmake_minimum_required(VERSION 3.17)
project(Boost_Echo_Client)

set(CMAKE_CXX_STANDARD 11)
set(Boost_USE_STATIC_LIBS OFF)
set(Boost_USE_MULTITHREADED ON)
set(Boost_USE_STATIC_RUNTIME OFF)
find_package(Boost 1.45.0 COMPONENTS filesystem regex)

if(Boost_FOUND)
    add_executable(Boost_Echo_Client ../src/BGRSClient.cpp ../src/ConnectionHandler.cpp ../src/InputHandler.cpp ../src/Receiver.cpp ../include/ConnectionHandler.h ../include/InputHandler.h ../include/Receiver.h)
    target_link_libraries(Boost_Echo_Client Boost::filesystem Boost::regex)
endif()
SET(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11 -pthread")
