cmake_minimum_required(VERSION 3.17)
project(test)

set(CMAKE_CXX_STANDARD 11)
set(Boost_USE_STATIC_LIBS OFF)
set(Boost_USE_MULTITHREADED ON)
set(Boost_USE_STATIC_RUNTIME OFF)
find_package(Boost 1.45.0 COMPONENTS filesystem regex)

if(Boost_FOUND)
    add_executable(test ../src/BGRSClient.cpp ../src/ConnectionHandler.cpp ../src/KeyboardReader.cpp ../src/SocketRW.cpp ../include/BGRSClient.h ../include/ConnectionHandler.h ../include/KeyboardReader.h ../include/SocketRW.h)
    target_link_libraries(test Boost::filesystem Boost::regex)
endif()
SET(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11 -pthread")
