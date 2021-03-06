
package neljansuora.dao;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import neljansuora.domain.User;
import java.util.HashMap;
import java.util.Collection;
import java.util.Scanner;

/**
  * This class handles services related to users in the db.
  */
public class FileUserDao implements UserDao {
    
    private HashMap<String, User> users;
    private String filename;
    
    /**
     * Constructor.
     * @param   filename    Name of the file used. Currently NeljanSuoraUi -class fetches it from config.properties when it creates the FileUserDao used throughout the programme. 
     */
    public FileUserDao(String filename) {
        this.users = new HashMap<>();
        this.filename = filename;
    }
    
  /**
    * This method adds a new User into FileUserDao with 0 wins and 0 losses.
    *
    * @param   username   Name of the new user
    * 
    * @return True, if a new User is actually added, false if the user already exists.
    */
    
    public boolean addUser(String username) {
        String key = modifyName(username);
        
        if (this.users.get(key) == null) {
            this.users.put(key, new User(username));
            return true;
        }
        //user already exists, return false
        return false;
    }
    
    
  /**
    * This method adds a new User into FileUserDao.
    *
    * @param   username   Name of the new user
    * @param   wins    Number of wins
    * @param   losses  Number of losses
    * 
    * @return True, if a new User is actually added, false if the user already exists.
    */
    public boolean addUser(String username, int wins, int losses) {
        String key = modifyName(username);
        
        if (this.users.get(key) == null) {
            this.users.put(key, new User(username, wins, losses));
            return true;
        }
        //user already exists, return false
        return false;
    }
    
  /**
    * This method adds a new User into FileUserDao.
    *
    * @param   user    User being added.
    * 
    * @return True, if a new User is actually added, false if the user already exists.
    */
    @Override
    public boolean addUser(User user) {
        //keys are added in trimmed, case insensitive form
        String key = modifyName(user.getName());
        if (this.users.get(key) == null) {
            this.users.put(key, user);
            return true;
        }
         //user already exists, return false
        return false;
    }
    
  /**
    * This method is used to test if an User with a specific name already exists in the FileUserDao.
    *
    * @param   name    Name of the user being searched for.
    * 
    * @return True, if the user exists and false if it doesn't.
    */
    
    @Override
    public boolean userExists(String name) {
        String editedName = modifyName(name);
        for (String username: this.users.keySet()) {
            if (username.equals(editedName)) {
                return true;
            }
        }
        
        return false;
    }
    
  /**
    * Reads users to FileUserDao from a file.
    */
    
    public void readFromFile() {
        try (Scanner reader = new Scanner(new File(this.filename))) {
            
            while (reader.hasNextLine()) {
                String currentRow = reader.nextLine();
                String[] userParameters = currentRow.split(";");
                User currentUser = new User(userParameters[0], Integer.valueOf(userParameters[1]), 
                        Integer.valueOf(userParameters[2]));
                addUser(currentUser);
            }
        } catch (Exception e) {
            saveToFile();
        }
    }
    
  /**
    * Saves users to a file from FileUserDao's list of users.
    */
    
    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(this.filename)) {
        
            for (User user: getUsers()) {
                writer.println(user);
            }

            writer.close();
        } catch (IOException e) {
            
        }
    }
    
  /**
    * Checks the number of all users.
    * @return Number of users.
    */
    public int userCount() {
        return this.users.size();
    }
    
    @Override
    public User getUser(String name) {
        return this.users.get(modifyName(name));
    }
    
    /**
     * Formats the usernames. Formatting is done so all usernames are handled case insensitively and without spaces.
     * @param   name    Username
     * @return Username transformed into a uniform format.
     */
    public String modifyName(String name) {
        return name.trim().toUpperCase();
    }
    
    /**
     * Gets all users in FileUserDao.
     * @return All current users in FileUserDao in a collection.
     */
    @Override
    public Collection<User> getUsers() {
        return this.users.values();
    }
    
    /**
    * Adds an user with updated variables over an existing user (if it has been added/it exists).
    * @param    user    User, whose variables are being changed.
    */
    @Override
    public void changeUserState(User user) {
        if (userExists(user.getName())) {
            this.users.put(modifyName(user.getName()), user);
        }
    }
    

}
