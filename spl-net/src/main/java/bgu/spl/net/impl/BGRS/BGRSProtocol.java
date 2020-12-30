package bgu.spl.net.impl.BGRS;

import bgu.spl.net.api.MessagingProtocol;

import java.time.LocalDateTime;

public class BGRSProtocol implements MessagingProtocol<String> {

    private boolean loggedIn;
    private String username;

    public BGRSProtocol(){
        loggedIn = false;
        username="";
    }

    private enum command{
        NO_USE,            //0
        ADMINREG,       //1
        STUDENTREG,     //2
        LOGIN,          //3
        LOGOUT,         //4
        COURSEREG,      //5
        KDAMCHECK,      //6
        COURSESTAT,     //7
        STUDENTSTAT,    //8
        ISREGISTERED,   //9
        UNREGISTER,     //10
        MYCOURSES,      //11
        ACK,            //12
        ERR             //13

    }
    @Override
    public String process(String msg) { //ADMINREG username password
        System.out.println("[" + LocalDateTime.now() + "]: " + msg);
        String[] splitMsg=msg.split(" ");
        if(splitMsg[0].equals(command.ADMINREG.toString())) return adminReg(splitMsg);
        else if(splitMsg[0].equals(command.STUDENTREG.toString())) return studentReg(splitMsg);
        else if(splitMsg[0].equals(command.LOGIN.toString())) return login(splitMsg);
        else if(splitMsg[0].equals(command.LOGOUT.toString())) return logout(splitMsg);
        else  if(splitMsg[0].equals(command.COURSEREG.toString())) return courseReg(splitMsg);
        else  if(splitMsg[0].equals(command.KDAMCHECK.toString())) return kdamCheck(splitMsg);
        else if(splitMsg[0].equals(command.COURSESTAT.toString())) return courseStatus(splitMsg);
        else if(splitMsg[0].equals(command.STUDENTSTAT.toString())) return studentStatus(splitMsg);
        else  if(splitMsg[0].equals(command.ISREGISTERED.toString())) return isRegistered(splitMsg);
        else   if(splitMsg[0].equals(command.UNREGISTER.toString())) return unregister(splitMsg);
        else  if(splitMsg[0].equals(command.MYCOURSES.toString())) return myCourses(splitMsg);

        return null;
    }
    private String adminReg(String[] str){
        //TODO: complete this
        //check if the str[1] is already register in the database - send ERROR 1
        //if not register, add new pair in database with username: str[1] and pass: str[2]
        return "ACK 1";
    }

    private String studentReg(String[]str){
        //TODO: complete this
        //check if the str[1] is already register in the database - return ERROR 2
        //if not register, add new pair in database with username: str[1] and pass: str[2]
        return "ACK 2";
    }

    private String login(String[]str){
        //TODO: complete this
        if(loggedIn){
            return "ERROR 3";
        }else{
            //check if the username: str[1] is not registered in the data base - return ERROR 3
            //check if the pass: str[2] does not match to the username: str[1] - return ERROR 3
            loggedIn =true;
            username=str[1];
        }
        return "ACK 3";
    }

    private String logout(String[]str){
        if(!loggedIn){
            return "ERROR 4";
        }
        else{
            loggedIn=false;
            username="";
        }
        return "ACK 4";
    }

    private String courseReg(String[]str){
        //TODO: complete this
        if(!loggedIn){
            return "ERROR 5";
        }
        else{
            //check if the course: str[1] exist in the Course.txt file - if not ERROR 5
            //check if a username is already registerd to the course: str[1] - if not return ERROR 5
            //check if a username have the course's kdam - if not return ERROR 5
            //register username to course:str[1] in database
        }
        return "ACK 5";
    }

    private String kdamCheck(String[]str){
        //TODO: complete this
        if(!loggedIn){
            return "ERROR 6";
        }
        else{
            //check if the course: str[1] exist in the Course.txt file - if not ERROR 6
            //return all the course: str[1]'s kdams
        }
        return "ACK 6";
    }

    private String courseStatus(String[]str){
        //TODO: complete this
        if(!loggedIn){
            return "ERROR 7";
        }
        else{
            //check if username is admin - if not return ERROR 7
            //check if the course: str[1] exist in the Course.txt file - if not ERROR 7
            //return the course: str[1] status from database
        }
        return "ACK 7";
    }
    private String studentStatus(String[]str){
        //TODO: complete this
        if(!loggedIn){
            return "ERROR 8";
        }else{
            //check if username is admin - if not return ERROR 8
            //check if the username: str[1] is not registered in the data base - return ERROR 8
            //return the username: str[1] status from database
        }
        return null;    //8 - only for AMIN
    }
    private String isRegistered(String[]str){
        //TODO: complete this
        if(!loggedIn){
            return err(command.ISREGISTERED);
        }
        //check in DB if str[1] is a course
        //check in the DB if the the student is register to course str[1]
        // return REGISTERED if yes
        // return NOT REGISTERED if no
        return null;    //9
    }
    private String unregister(String[]str){
        //TODO: complete this
        if(!loggedIn){
            return err(command.UNREGISTER);
        }
        //check in DB if str[1] is a course
        //check in the DB if the the student is not register to course str[1]
        // return REGISTERED if yes
        // return NOT REGISTERED if no
        return ack(command.UNREGISTER);    //10
    }
    private String myCourses(String[]str){
        if(!loggedIn){
            return err(command.UNREGISTER);
        }
        //return ack(command.MYCOURSES, DataBase.getCourses());    //11
        return null;
    }
    private String ack(command c){
        return "ACK "+ c;
    }

    private String ack(command c, String ackMsg){
        return "ACK "+ c + " " + ackMsg;
    }
    private String err(command c){
        return "ERR " + c;
    }
    private String err(command c, String errMsg){
        return "ERR " + c + " " + errMsg;
    }


    @Override
    public boolean shouldTerminate() {
        return false;
    }
}
