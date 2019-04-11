
package neljansuora.dao;
import java.io.File;
import java.io.PrintWriter;
import neljansuora.domain.User;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import neljansuora.dao.UserDao;


public class FileUserDao implements UserDao {
    
    private HashMap<String, User> users;
    private String filename;
    
    
    public FileUserDao(String filename) {
        this.users = new HashMap<>();
        this.filename = filename;
        readFromFile();
    }
    
    public boolean addUser(String username) {
        String key = modifyName(username);
        
        if (this.users.get(key) == null) {
            this.users.put(key, new User(username));
            return true;
        }
        //käyttäjä on jo olemassa, joten palautetaan false
        return false;
    }
    
    public boolean addUser(String username, int wins, int losses) {
        String key = modifyName(username);
        
        if (this.users.get(key) == null) {
            this.users.put(key, new User(username, wins, losses));
            return true;
        }
        //käyttäjä on jo olemassa, joten palautetaan false
        return false;
    }
    
    
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
    
    //add addUser(Collection collection) -method for future db needs
    
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
    
    public void saveToFile() throws Exception {
        PrintWriter writer = new PrintWriter(this.filename);
        
        for (User user: getUsers()) {
            writer.println(user);
        }
        
        writer.close();
    }
    
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
    
    @Override
    public void changeUserState(User user) {
        
    }
    

}
