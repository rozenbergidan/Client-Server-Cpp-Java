package bgu.spl.net.impl.BGRS;

import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.impl.Database.Database;

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

        return err(Short.parseShort(splitMsg[0]));
    }

    private String adminReg(String[] str){
        //check if the str[1] is already register in the database - send ERROR 1
        //if not register, add new pair in database with username: str[1] and pass: str[2]
        try{
        Database.getInstance().adminReg(str[1],str[2]);
        }
        catch(Exception e){
            return err(command.ADMINREG);
        }
        return ack(command.ADMINREG);
    }

    private String studentReg(String[]str){
        //check if the str[1] is already register in the database - return ERROR 2
        //return "ERROR 2 - Student already registered"
        //if not register, add new pair in database with username: str[1] and pass: str[2]
        try{
        Database.getInstance().studentReg(str[1],str[2]);
        }catch(Exception e){
            return err(command.STUDENTREG);
        }
        return ack(command.STUDENTREG);
    }

    private String login(String[]str){
        if(loggedIn){
            return err(command.LOGIN);
        }else {
            //check if the username: str[1] is not registered in the data base - return ERROR 3
            //check if the pass: str[2] does not match to the username: str[1] - return ERROR 3
            try{
            username=Database.getInstance().login(str[1], str[2]);
            loggedIn=true;
        }catch(Exception e){
                return err(command.LOGIN);
            }
        }
        return ack(command.LOGIN);
    }

    private String logout(String[]str){
        if(!loggedIn){
            return err(command.LOGOUT);
        }
        else{
            try {
                Database.getInstance().logout(username);
                loggedIn = false;
                username = "";
            }catch(Exception e){
                return err(command.LOGOUT);
            }
        }
        return ack(command.LOGOUT);
    }

    private String courseReg(String[]str){
        if(!loggedIn){
            return err(command.COURSEREG);
        }
        else {
            //check if the course: str[1] exist in the Course.txt file - if not ERROR 5
            //check if a username is already registerd to the course: str[1] - if not return ERROR 5
            //check if a username have the course's kdam - if not return ERROR 5
            //register username to course:str[1] in database
            try{
            Database.getInstance().courseReg(str[1],username);
            }catch(Exception e){
                return err(command.COURSEREG);
            }
        }
        return ack(command.COURSEREG);
    }

    private String kdamCheck(String[]str){
        if(!loggedIn){
            return err(command.KDAMCHECK);
        }
        else{
            //check if the course: str[1] exist in the Course.txt file - if not ERROR 6
            //return all the course: str[1]'s kdams
            try{
            return ack(command.KDAMCHECK, Database.getInstance().kdamCheck(str[1]));
            }catch(Exception e){
                return err(command.KDAMCHECK);
            }
        }
    }

    private String courseStatus(String[]str){
        if(!loggedIn){
            return err(command.COURSESTAT);
        }
        else{
            //check if username is admin - if not return ERROR 7
            //check if the course: str[1] exist in the Course.txt file - if not ERROR 7
            //return the course: str[1] status from database
            try{
                return ack(command.COURSESTAT, Database.getInstance().courseStatus(str[1],username));
            }catch(Exception e){
                return err(command.COURSESTAT);
            }
        }
    }
    private String studentStatus(String[]str){
        if(!loggedIn){
            return err(command.STUDENTSTAT);
        }
        String output = "Student: "+ username + "\nCourses: ";
        try{
            output = output + Database.getInstance().studentStatus(str[1], username);
        }catch (Exception e) { return err(command.STUDENTSTAT);}
        return ack(command.STUDENTSTAT, output);    //8 - only for AMIN
    }


    private String isRegistered(String[]str){
        if(!loggedIn){
            return err(command.ISREGISTERED);
        }
        try {
            return ack(command.ISREGISTERED,Database.getInstance().isRegistered(str[1], username));
        }catch (Exception e){ return err(command.ISREGISTERED);}
    }


    private String unregister(String[]str){
        if(!loggedIn){
            return err(command.UNREGISTER);
        }
        try{
            Database.getInstance().unregister(str[1], username);
        }catch (Exception e) {return err(command.UNREGISTER);}

        return ack(command.UNREGISTER);    //10
    }

    private String myCourses(String[]str){
        String output;
        if(!loggedIn){
            return err(command.MYCOURSES);
        }
        try {
            return ack(command.MYCOURSES, Database.getInstance().myCourses(username));
        }catch (Exception e) {return err(command.MYCOURSES);}
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
    private String err(short srt){
        return "ERR " + srt;
    }


    @Override
    public boolean shouldTerminate() {
        return false;
    }
}
