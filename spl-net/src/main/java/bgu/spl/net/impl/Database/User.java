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
    public String getUsername(){
        return username;
    }
    public boolean checkPassword(String _password){
        return password.equals(_password);
    }
    public boolean login(){
        return isLoggedIn.compareAndSet(false,true);
    }
    public void logout(){
        isLoggedIn.compareAndSet(true,false);
    }
}
