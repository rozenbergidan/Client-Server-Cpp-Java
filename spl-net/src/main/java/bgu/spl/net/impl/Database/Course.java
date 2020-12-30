package bgu.spl.net.impl.Database;

import java.util.LinkedList;

public class Course {
    public String id;
    public String name;
    public int limit;
    public int capacity;
    public LinkedList<String> students;
    public LinkedList<String> kdams;
    public Course(String _id, String _name, int _limit,LinkedList<String> _kdams){
        id=_id;
        name=_name;
        limit=_limit;
        capacity=0;
        students=new LinkedList<>();//TODO: check what is necessary in the aspect of concurrency!
        kdams=_kdams;
    }
    public LinkedList<String> getKdams(){
        return kdams;
    }
    public synchronized void register(String student) throws Exception{
        if (students.contains(student)) throw new Exception("student already registered to this course");
        if (capacity > limit)  throw new Exception("tried to registered to full course");
            students.add(student);
            capacity++;
    }

    public synchronized void unregister(String student) throws Exception{
        if (!students.contains(student)) throw new Exception("Tried to unregister, but wasnt registered");
        students.remove(student);
        capacity--;
    }

    public String status(){
        //TODO: need to write sorting studuents alphabeticly
        return "("+id+") "+name+"\n"+"Seats available: "+capacity+"\\"+limit+"\n"+"Students Registered:"+students.toString()+"\n";

    }

}
