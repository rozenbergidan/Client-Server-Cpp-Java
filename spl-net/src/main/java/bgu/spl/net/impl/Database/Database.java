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
    private ConcurrentHashMap<Integer, Course> courses;

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


}
