package bgu.spl.net.impl.Database;

import java.util.Comparator;
import java.util.LinkedList;

public class Student extends User {
    private LinkedList<String> courses;

    public Student(String _username, String _password){
        super(_username,_password);
        courses=new LinkedList<>();
    }

    public void unregister(String Cid){
        courses.remove(Cid);
    }

    public void register(String courseNum){
        courses.add(courseNum);
        sort();
    }



    private void sort(){
        courses.sort(Comparator.comparingInt(x -> Database.getInstance().getIndex(x)));
        }

    public boolean hasKdam(String cid){
        for (String course:courses) {
            if(cid.equals(course))
                return true;
        }
        return false;
    }
    public LinkedList<String> myCourses(){
        return courses;
    }

}
