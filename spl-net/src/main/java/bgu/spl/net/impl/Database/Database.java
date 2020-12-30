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


    public void adminReg(String username, String password){
        String lusername=username.toLowerCase();
        if(admins.containsKey(lusername)){
            throw new IllegalArgumentException("Admin is already registered");
        }
        if(students.containsKey(lusername)){
            throw new IllegalArgumentException("A Student has already registered with the same usename");
        }
        admins.put(lusername, new Admin(lusername,password));

    }

    public void studentReg(String username, String password){
        String lusername=username.toLowerCase();
        if(students.containsKey(lusername)){
            throw new IllegalArgumentException("Student is already registered");
        }
        if(admins.containsKey(lusername)){
            throw new IllegalArgumentException("An Admin has already registered with the same username");
        }
        students.put(lusername, new Student(lusername,password));
    }

    public User login(String username, String password){
        String lusername=username.toLowerCase();
        if(admins.containsKey(lusername) & students.containsKey(lusername)){
            throw new IllegalArgumentException("User does not exist");
        }
        if(admins.containsKey(lusername)){
            if(admins.get(lusername).loggedIn()){
                if(admins.get(lusername).checkPassword(password)){
                    throw new IllegalArgumentException("Password does not match");
                }
               throw new IllegalArgumentException("Already logged in");
            }
            return admins.get(lusername);
        } else if(students.containsKey(lusername)){
            if(students.get(lusername).loggedIn()) {
                if (students.get(lusername).checkPassword(password)) {
                    throw new IllegalArgumentException("Password does not match");
                }
                throw new IllegalArgumentException("Already logged in");
            }
            return students.get(lusername);
        }


    }

}
