package bgu.spl.net.impl.Database;

import java.util.LinkedList;

public class User {
    public String username;
    public String password;
    public LinkedList<String> courses;

    public User(String _username,String _password){
        username=_username;
        password=_password;
        courses=new LinkedList<>();
    }

    public void register(String courseNum){
        courses.add(courseNum);
    }

    public String status(){
        String output="Student: "+username+"\n";
        output=output+"Courses: "+courses.toString()+"\n";
        return output;

    }
}
