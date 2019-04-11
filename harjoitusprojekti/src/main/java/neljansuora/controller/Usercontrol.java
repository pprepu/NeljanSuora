
package neljansuora.controller;
import java.io.File;
import java.io.PrintWriter;
import neljansuora.domain.User;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import neljansuora.dao.UserDao;


public class Usercontrol {
    
    private User currentUser;
    
    
    public Usercontrol() {
        this.currentUser = null;
    }
    
    
    public void logIn(User user) {
        setCurrentUser(user);
    }
    
    public void logOut() {
        this.currentUser = null;
    }
    
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    
    public User getCurrentUser() {
        return this.currentUser;
    }
    
    public void win() {
        getCurrentUser().addWin();
    }
    
    public void lose() {
        getCurrentUser().addLoss();
    }

}
