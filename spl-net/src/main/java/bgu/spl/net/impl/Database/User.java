package bgu.spl.net.impl.Database;

import java.util.LinkedList;

public class User {
    public String username;
    public String password;
    public LinkedList<String> courses;
    public boolean admin;

    public User(String _username,String _password, boolean _admin){
        username=_username;
        password=_password;
        courses=new LinkedList<>();
        admin=_admin;
    }

    public void register(String courseNum){
        courses.add(courseNum);
    }

    public String status(){
        return "Student: "+username+"\n"+"Courses: "+courses.toString()+"\n";

    }
}
