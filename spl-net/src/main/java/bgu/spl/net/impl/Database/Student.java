package bgu.spl.net.impl.Database;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Student {
    private String username;
    private String password;
    private LinkedList<String> courses;
    private AtomicBoolean isLoggedIn;

    public Student(String _username, String _password, boolean _admin){
        username=_username;
        password=_password;
        courses=new LinkedList<>();
        isLoggedIn=new AtomicBoolean(false);
    }

    public boolean loggedIn(){
        return isLoggedIn.compareAndSet(false,true);
    }
    public void loggedOut(){
        isLoggedIn.compareAndSet(true,false);
    }
    public void register(String courseNum){
        courses.add(courseNum);
    }

    public String status(){
        //TODO: need to write sorting to courses as it is in the Course.txt
        return "Student: "+username+"\n"+"Courses: "+courses.toString()+"\n";

    }

    public LinkedList<String> myCourses(){
        return courses;
    }

}
