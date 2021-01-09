package bgu.spl.net.impl.Database;

import java.util.Comparator;
import java.util.LinkedList;

public class Course {
    public String id;
    public String name;
    public int limit;
    public int capacity;
    public LinkedList<String> students;
    public LinkedList<String> kdams;
    public int index;

    public Course(String _id, String _name, int _limit,LinkedList<String> _kdams, int _index){
        id=_id;
        name=_name;
        limit=_limit;
        capacity=0;
        students=new LinkedList<>();
        kdams=_kdams;
        index=_index;
    }
    private void sort(){
        kdams.sort(Comparator.comparingInt(x -> Database.getInstance().getIndex(x)));
    }

    public LinkedList<String> getKdams(){
        sort();
        return kdams;
    }
    public synchronized void register(String student) throws Exception{
        if (students.contains(student)) throw new Exception("student already registered to this course");
        if (capacity >= limit)  throw new Exception("tried to registered to full course");
            students.add(student);
            java.util.Collections.sort(students);
            capacity++;
    }

    public synchronized void unregister(String student) throws Exception{
        if (!students.contains(student)) throw new Exception("Tried to unregister, but wasnt registered");
        students.remove(student);
        capacity--;
    }

    public String isRegistered(String student){
        if( students.contains(student)) return "REGISTERED";
        return "NOT REGISTERED";
    }

    public String status(){
        return "("+id+") "+name+"\n"+"Seats available: "+capacity+"\\"+limit+"\n"+"Students Registered:"+students.toString();

    }
}
