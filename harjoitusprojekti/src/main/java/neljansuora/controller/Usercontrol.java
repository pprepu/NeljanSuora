
package neljansuora.controller;
import neljansuora.domain.User;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Usercontrol {
    // for now, no permanent data is linked to created users
    
    private HashMap<String, User> users;
    
    public Usercontrol(HashMap<String, User> users) {
        this.users = users;
    }
    
    public boolean addUser(String username) {
        String key = username.trim().toUpperCase();
        
        if (this.users.get(key) == null) {
            this.users.put(key, new User(username));
            return true;
        }
        //käyttäjä on jo olemassa, joten palautetaan false
        return false;
    }
    
    public boolean addUser(User user) {
        //käyttäjien avaimet lisätään muokatussa muodossa
        String key = user.getNameModified();
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
    
    public boolean userExists(String name) {
        String editedName = name.trim().toUpperCase();
        for (String username: this.users.keySet()) {
            if (username.equals(editedName)) {
                return true;
            }
        }
        
        return false;
    }
    
    public int userCount() {
        return this.users.size();
    }
    
    public User getUser(String name) {
        return this.users.get(name);
    }
    
    public Collection<User> getUsers() {
        return this.users.values();
    }

}
