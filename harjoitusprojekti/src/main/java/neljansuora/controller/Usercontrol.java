
package neljansuora.controller;
import neljansuora.domain.User;

/**
 * Controls actions related to the logged in Users (main user and player 2 for the game), such as adding losses and wins to them. 
 * Also takes care of the logging in/out itself.
 */
public class Usercontrol {
    
    private User currentUser;
    private User player2;
    
    /**
     * Constructor - sets currentUser and player2 when created.
     */
    public Usercontrol() {
        this.currentUser = null;
        this.player2 = null;
    }
    
    /**
    * Logging in.
    * 
    * @param   user   The main user who is being logged in/added.
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
     * Practically represents a login function for player 2.
     * @param   user    User for player 2. 
     */
    public void setPlayer2(User user) {
        this.player2 = user;
    }
    
    public User getPlayer2() {
        return this.player2;
    }
    /**
    * Adds a win to logged in main User.
    */
    public void win() {
        getCurrentUser().addWin();
    }
    /**
    * Adds loss to logged in main User.
    */
    public void lose() {
        getCurrentUser().addLoss();
    }
    /**
    * Adds a win to logged in player 2.
    */
    public void p2Win() {
        getPlayer2().addWin();
    }
    /**
    * Adds a loss to logged in player 2.
    */
    public void p2Lose() {
        getPlayer2().addLoss();
    }
    
}
