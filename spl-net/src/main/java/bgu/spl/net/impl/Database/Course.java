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
        students=new LinkedList<>();//TODO: check what is necessary in the aspect of concurrency!
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
        String output="("+id+") "+name+"\n";
        output=output+"Seats available: "+capacity+"\\"+limit+"\n";
        output=output+"Students Registered:"+students.toString()+"\n";
        return output;
    }

}
