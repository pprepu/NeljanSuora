
package neljansuora.domain;

/**
 * Represents players of the game, whose statistics are saved into a database.
 * Implements Comparable because sorting is needed in statistics-view, where top winners need to be arranged.
 */
public class User implements Comparable<User> {
    
    private String name;
    private int wins;
    private int losses;

    /**
     * Constructor with only the name -parameter.
     * Create a new user with 0 wins and 0 losses.
     * @param   name    Username
     */
    public User(String name) {
        this.name = name;
        this.wins = 0;
        this.losses = 0;
    }
    
    /**
     * Constructor with all parameters.
     * @param   name    Username.
     * @param   wins    Number of wins.
     * @param   losses   Number of losses.
     */
    public User(String name, int wins, int losses) {
        this.name = name;
        this.wins = wins;
        this.losses = losses;
    }
   
    public String getName() {
        return this.name;
    }
    
    public int getWins() {
        return wins;
    }
    
    public int getLosses() {
        return losses;
    }
    
    /**
    * Adds a win to the user.
    */
    public void addWin() {
        this.wins++;
    }
    
    /**
    * Adds a loss to the user.
    */
    public void addLoss() {
        this.losses++;
    }
    
    /**
     * This method is primarily used to define how users are sorted, which is important in the stats-view where top-winners need to be found.
     * @param   user    Another user this user is compared to.
     * @return  An integer which defines how users are sorted.
     */
    @Override
    public int compareTo(User user) {
        return user.getWins() - this.wins;
    }
        
    /**
     * @return  User's current values in a string, which FileUserDao -class know how to turn into a User.
     */
    @Override
    public String toString() {
        return this.name + ";" + this.wins + ";" + this.losses;
    }
}
