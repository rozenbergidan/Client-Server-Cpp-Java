package bgu.spl.net.impl.BGRS;

import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.impl.Database.Database;

import java.time.LocalDateTime;

public class BGRSProtocol implements MessagingProtocol<String> {

    private boolean loggedIn;
    private String username;
    private boolean shouldTerminate;

    final short ADMINREG = 1;
    final short STUDENTREG=2;
    final short LOGIN=3;
    final short LOGOUT=4;
    final short COURSEREG=5;
    final short KDAMCHECK=6;
    final short COURSESTAT=7;
    final short STUDENTSTAT=8;
    final short ISREGISTERED=9;
    final short UNREGISTER=10;
    final short MYCOURSES=11;
    final short ACK=12;
    final short ERR=13;

    public BGRSProtocol(){
        loggedIn = false;
        username="";
        shouldTerminate=false;
    }

    @Override
    public String process(String msg) { //ADMINREG username password
        System.out.println("[" + LocalDateTime.now() + "]: " + msg);
        String[] splitMsg=msg.split(" ");
        short opCode = Short.parseShort(splitMsg[0]);
        if(opCode == ADMINREG){
            return adminReg(splitMsg);
        }
        else if(opCode == STUDENTREG) return studentReg(splitMsg);
        else if(opCode == LOGIN) return login(splitMsg);
        else if(opCode ==LOGOUT) return logout(splitMsg);
        else  if(opCode == COURSEREG) return courseReg(splitMsg);
        else  if(opCode == KDAMCHECK) return kdamCheck(splitMsg);
        else if(opCode == COURSESTAT) return courseStatus(splitMsg);
        else if(opCode == STUDENTSTAT) return studentStatus(splitMsg);
        else  if(opCode == ISREGISTERED) return isRegistered(splitMsg);
        else   if(opCode == UNREGISTER) return unregister(splitMsg);
        else  if(opCode == MYCOURSES) return myCourses(splitMsg);

        return err(opCode);
    }

    private String adminReg(String[] str) {
        //check if the str[1] is already register in the database - send ERROR 1
        //if not register, add new pair in database with username: str[1] and pass: str[2]
        //if the client is logged in - send ERROR 1
        if (loggedIn) {
            return err(ADMINREG);
        } else {
            try {
                Database.getInstance().adminReg(str[1], str[2]);
            } catch (Exception e) {
                return err(ADMINREG);
            }
            return ack(ADMINREG);
        }
    }
    private String studentReg(String[]str) {
        //check if the str[1] is already register in the database - return ERROR 2
        //return "ERROR 2 - Student already registered"
        //if not register, add new pair in database with username: str[1] and pass: str[2]
        //if the client is logged in - send ERROR 2
        if (loggedIn) {
            return err(STUDENTREG);
        } else {
            try {
                Database.getInstance().studentReg(str[1], str[2]);
            } catch (Exception e) {
                return err(STUDENTREG);
            }
            return ack(STUDENTREG);
        }
    }

    private String login(String[]str){
        if(loggedIn){
            return err(LOGIN);
        }else {
            //check if the username: str[1] is not registered in the data base - return ERROR 3
            //check if the pass: str[2] does not match to the username: str[1] - return ERROR 3
            try{
            username=Database.getInstance().login(str[1], str[2]);
            loggedIn=true;
        }catch(Exception e){
                return err(LOGIN);
            }
        }
        return ack(LOGIN);
    }

    private String logout(String[]str){
        if(!loggedIn){
            return err(LOGOUT);
        }
        else{
            try {
                Database.getInstance().logout(username);
                loggedIn = false;
                username = "";
                shouldTerminate=true;

            }catch(Exception e){
                return err(LOGOUT);
            }
        }
        return ack(LOGOUT);
    }

    private String courseReg(String[]str){
        if(!loggedIn){
            return err(COURSEREG);
        }
        else {
            //check if the course: str[1] exist in the Course.txt file - if not ERROR 5
            //check if a username is already registerd to the course: str[1] - if not return ERROR 5
            //check if a username have the course's kdam - if not return ERROR 5
            //register username to course:str[1] in database
            try{
            Database.getInstance().courseReg(str[1],username);
            }catch(Exception e){
                return err(COURSEREG);
            }
        }
        return ack(COURSEREG);
    }

    private String kdamCheck(String[]str){
        if(!loggedIn){
            return err(KDAMCHECK);
        }
        else{
            //check if the course: str[1] exist in the Course.txt file - if not ERROR 6
            //return all the course: str[1]'s kdams
            try{
            return ack(KDAMCHECK, Database.getInstance().kdamCheck(str[1]));
            }catch(Exception e){
                return err(KDAMCHECK);
            }
        }
    }

    private String courseStatus(String[]str){
        if(!loggedIn){
            return err(COURSESTAT);
        }
        else{
            //check if username is admin - if not return ERROR 7
            //check if the course: str[1] exist in the Course.txt file - if not ERROR 7
            //return the course: str[1] status from database
            try{
                return ack(COURSESTAT, Database.getInstance().courseStatus(str[1],username));
            }catch(Exception e){
                return err(COURSESTAT);
            }
        }
    }
    private String studentStatus(String[]str){
        if(!loggedIn){
            return err(STUDENTSTAT);
        }
        String output = "Student: "+ str[1] + "\nCourses: ";
        try{
            output = output + Database.getInstance().studentStatus(str[1], username);
        }catch (Exception e) { return err(STUDENTSTAT);}
        return ack(STUDENTSTAT, output);
    }


    private String isRegistered(String[]str){
        if(!loggedIn){
            return err(ISREGISTERED);
        }
        try {
            return ack(ISREGISTERED,Database.getInstance().isRegistered(str[1], username));
        }catch (Exception e){ return err(ISREGISTERED);}
    }


    private String unregister(String[]str){
        if(!loggedIn){
            return err(UNREGISTER);
        }
        try{
            Database.getInstance().unregister(str[1], username);
        }catch (Exception e) {return err(UNREGISTER);}

        return ack(UNREGISTER);
    }

    private String myCourses(String[]str){
        if(!loggedIn){
            return err(MYCOURSES);
        }
        try {
            return ack(MYCOURSES, Database.getInstance().myCourses(username));
        }catch (Exception e) {return err(MYCOURSES);}
    }

    private String ack(short c){
        return "ACK "+ c;
    }

    private String ack(short c, String ackMsg){
        return "ACK "+ c + " " + ackMsg;
    }


    private String err(short srt){
        return "ERR " + srt;
    }


    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }
}
