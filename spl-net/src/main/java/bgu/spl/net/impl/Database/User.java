package bgu.spl.net.impl.Database;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class User {
    protected String username;
    protected String password;
    protected AtomicBoolean isLoggedIn;

    public User(String _username, String _password){
        username=_username;
        password=_password;
        isLoggedIn=new AtomicBoolean(false);
    }
    protected boolean checkPassword(String _password){
        return password.equals(_password);
    }
    public void login(String _password)throws Exception{
        if(!checkPassword(_password)){
            throw new Exception("Password does not match");
        }
        if(!isLoggedIn.compareAndSet(false,true)){
            throw new Exception("Already logged in");
        }
    }
    public void logout(){
        isLoggedIn.compareAndSet(true,false);
    }
}
