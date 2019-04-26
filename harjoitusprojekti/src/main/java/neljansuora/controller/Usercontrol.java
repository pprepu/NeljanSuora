
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

/**
 * Controls actions related to the logged in User, such as adding losses and wins to it. 
 * Also takes care of the logging in/out itself.
 */
public class Usercontrol {
    
    private User currentUser;
    
    
    public Usercontrol() {
        this.currentUser = null;
    }
    
    /**
    * Logging in.
    * 
    * @param   user   The user thas is being logged in/added.
    * 
    */
    public void logIn(User user) {
        setCurrentUser(user);
    }
    
    /**
    * Logging out.
    */
    public void logOut() {
        this.currentUser = null;
    }
    
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    
    public User getCurrentUser() {
        return this.currentUser;
    }

    /**
    * Adds a win to logged in User.
    */
    public void win() {
        getCurrentUser().addWin();
    }
    /**
    * Adds loss to logged in User.
    */
    public void lose() {
        getCurrentUser().addLoss();
    }

}
