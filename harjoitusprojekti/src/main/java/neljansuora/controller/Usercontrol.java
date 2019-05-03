
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
    private User player2;
    
    
    public Usercontrol() {
        this.currentUser = null;
        this.player2 = null;
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
    
    public void setPlayer2(User user) {
        this.player2 = user;
    }
    
    public User getPlayer2() {
        return this.player2;
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
    
    public void p2Win() {
        getPlayer2().addWin();
    }
    
    public void p2Lose() {
        getPlayer2().addLoss();
    }
    

}
