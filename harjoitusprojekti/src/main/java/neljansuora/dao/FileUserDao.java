
package neljansuora.dao;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import neljansuora.domain.User;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import neljansuora.dao.UserDao;

/**
  * This class handles services related to users in the db.
  */
public class FileUserDao implements UserDao {
    
    private HashMap<String, User> users;
    private String filename;
    
    
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
        //käyttäjä on jo olemassa, joten palautetaan false
        return false;
    }
    
    
  /**
    * This method adds a new User into FileUserDao.
    *
    * @param   username   Name of the new user
    * @param   wins    Number of wins
    * @param   losses  Numbef of losses
    * 
    * @return True, if a new User is actually added, false if the user already exists.
    */
    public boolean addUser(String username, int wins, int losses) {
        String key = modifyName(username);
        
        if (this.users.get(key) == null) {
            this.users.put(key, new User(username, wins, losses));
            return true;
        }
        //käyttäjä on jo olemassa, joten palautetaan false
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
        //käyttäjien avaimet lisätään muokatussa muodossa
        String key = modifyName(user.getName());
        //tarkistetaan sisältääkö hajautustaulu jo kyseisen käyttäjän
        //mikäli ei, lisätään uusi käyttäjä ja palautetaan true
        if (this.users.get(key) == null) {
            this.users.put(key, user);
            return true;
        }
        //käyttäjä on jo olemassa, joten palautetaan false
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
            System.out.println("Error: " + e.getMessage());
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
            System.out.println("Error: " + e.getMessage());
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
    
    private String modifyName(String name) {
        return name.trim().toUpperCase();
    }
    
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
