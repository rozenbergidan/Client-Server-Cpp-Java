package bgu.spl.net.impl.Database;

import java.util.Arrays;
import java.util.LinkedList;
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

    private static class DatabaseHolder{
        private static Database instance = new Database();
    }
    //to prevent user from creating new Database
    private Database() {
        students=new ConcurrentHashMap<>();
        admins=new ConcurrentHashMap<>();
        courses=new ConcurrentHashMap<>();
    }

    /**
     * Retrieves the single instance of this class.
     */
    public static Database getInstance() {
        return DatabaseHolder.instance;
    }
    []
    /**
     * loades the courses from the file path specified
     * into the Database, returns true if successful.
     */
    public boolean initialize(String coursesFilePath) {
        // TODO: implement
        String[] lines = coursesFilePath.split("\n");
        for (String line:lines) {
            String[]data = line.split("|");
            LinkedList<String> kdam=new LinkedList<>();
            if(data[2].length()!=2) {
                String kdams = data[2].substring(1, data[2].length() - 1);
                String[] kdamsCourses = kdams.split(",");
                kdam.addAll(Arrays.asList(kdamsCourses));
            }
            courses.put(data[0], new Course(data[0],data[1],Integer.parseInt(data[3]),kdam));
        }
        return true;
    }


    public void adminReg(String username, String password)throws Exception{
        String lusername=username.toLowerCase();
        if(admins.containsKey(lusername)){
            throw new Exception("Admin is already registered");
        }
        if(students.containsKey(lusername)){
            throw new Exception("A Student has already registered with the same usename");
        }
        admins.put(lusername, new Admin(lusername,password));

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



    public void studentReg(String username, String password)throws Exception{
        String lusername=username.toLowerCase();
        if(students.containsKey(lusername)){
            throw new Exception("Student is already registered");
        }
        if(admins.containsKey(lusername)){
            throw new Exception("An Admin has already registered with the same username");
        }
        students.put(lusername, new Student(lusername,password));
    }

    public String login(String username, String password)throws Exception{
        String lusername=username.toLowerCase();
        if(admins.containsKey(lusername) & students.containsKey(lusername)){
            throw new Exception("User does not exist");
        }
        if(admins.containsKey(lusername)){
            if(admins.get(lusername).login()){
                if(admins.get(lusername).checkPassword(password)){
                    throw new Exception("Password does not match");
                }
               throw new Exception("Already logged in");
            }
        } else if(students.containsKey(lusername)){
            if(students.get(lusername).login()) {
                if (students.get(lusername).checkPassword(password)) {
                    throw new Exception("Password does not match");
                }
                throw new Exception("Already logged in");
            }
        }
        return username;
    }

    public void logout(String username){
        String lusername=username;
        if(admins.containsKey(lusername)){
            admins.get(lusername).logout();
        }
        else if(students.containsKey(lusername)){
            students.get(lusername).logout();
        }
    }

    public void courseReg(String Cid, String student)throws Exception{
        String lusername=student;
        if(admins.containsKey(lusername)){
            throw new Exception("Admins cant register to courses");
        }else{
            if(!courses.containsKey(Cid)){
                throw new Exception("Course does not Exist");
            }else{
                for (String courseKdam:courses.get(Cid).getKdams()) {
                    if(!students.get(lusername).hasKdam(courseKdam)){
                        throw new Exception("Students does not have the necessary kdams");
                    }
                }
                try {
                    courses.get(Cid).register(lusername);
                    students.get(lusername).register(Cid);
                }catch(Exception e){
                    throw e;
                }
            }
        }
    }
    public String  kdamCheck(String Cid) throws Exception{
        if(!courses.containsKey(Cid)){
            throw new Exception("Course does not Exist");
        }else{
            return courses.get(Cid).getKdams().toString();
        }
    }


}
