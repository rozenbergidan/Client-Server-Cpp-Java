package bgu.spl.net.impl.Database;

import java.util.LinkedList;

public class Course {
    public String id;
    public String name;
    public int limit;
    public int capacity;
    public LinkedList<String> students;

    public Course(String _id, String _name, int _limit){
        id=_id;
        name=_name;
        limit=_limit;
        capacity=0;
        students=new LinkedList<>();
    }

    public void register(String student) {
        if (capacity < limit) {
            students.add(student);
            capacity++;
        }
        else{
            //TODO: complete this
            //throw error of there is no place in course
        }
    }

    public String status(){
        return "("+id+") "+name+"\n"+"Seats available: "+capacity+"\\"+limit+"\n"+"Students Registered:"+students.toString()+"\n";

    }

}
