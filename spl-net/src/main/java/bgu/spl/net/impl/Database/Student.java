package bgu.spl.net.impl.Database;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Student extends User {
    private LinkedList<String> courses;

    public Student(String _username, String _password){
        super(_username,_password);
        courses=new LinkedList<>();
    }


    public void register(String courseNum){
        courses.add(courseNum);
    }

    public String status(){
        //TODO: need to write sorting to courses as it is in the Course.txt
        return "Student: "+username+"\n"+"Courses: "+courses.toString()+"\n";

    }
}
