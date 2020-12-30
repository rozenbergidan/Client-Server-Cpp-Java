package bgu.spl.net.impl.Database;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Passive object representing the Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
public class Database {
    private ConcurrentHashMap<String, Student> students;
    private ConcurrentHashMap<String, Admin> admins;
    private ConcurrentHashMap<String, Course> courses;

    //to prevent user from creating new Database
    private Database() {
        // TODO: implement
    }

    /**
     * Retrieves the single instance of this class.
     */
    public static Database getInstance() {
        //TODO: implement
        return null;
    }

    /**
     * loades the courses from the file path specified
     * into the Database, returns true if successful.
     */
    public boolean initialize(String coursesFilePath) {
        // TODO: implement
        return false;
    }

    /**
     *
     */
    public boolean adminReg(String username, String password){
        return false;
    }

    /**
     *
     */
    public String studentStatus(String student, String admin) throws Exception{
        if(!admins.containsKey(admin)) throw new Exception("the user that call this function is not an admin");
        if(!students.containsKey(student)) throw new Exception("the user that call this function is not a student");
        return students.get(student).myCourses().toString();

    }
    /**
     *
     */
    public String isRegistered(String Cid, String student) throws Exception{
        if (!courses.containsKey(Cid)) throw new Exception("course id not exsist");
        if(!students.containsKey(student)) throw new Exception("the user that call this function is not a student");
        return courses.get(Cid).isRegistered(student);

    }
    /**
     *
     */
    public void unregister(String Cid, String student) throws Exception{
        if (!courses.containsKey(Cid)) throw new Exception("course id not exsist");
        if(!students.containsKey(student)) throw new Exception("the user that call this function is not a student");
        courses.get(Cid).unregister(student);
    }

    public String myCourses(String student) throws Exception{
        if(!students.containsKey(student)) throw new Exception("the user that call this function is not a student");
        return students.get(student).myCourses().toString();
    }





}
