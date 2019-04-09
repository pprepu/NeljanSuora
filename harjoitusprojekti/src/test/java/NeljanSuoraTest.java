/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Collection;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import neljansuora.controller.Usercontrol;
import neljansuora.domain.User;
import neljansuora.controller.Gamecontrol;

/**
 *
 * @author annika
 */
public class NeljanSuoraTest {
    
    User defaultUser;
    Usercontrol control;
    
    @Before
    public void setUp() {
        defaultUser = new User("Petteri");
        control = new Usercontrol(new HashMap<>());
    }
    
    
    @Test
    public void getNameinUserIsWorkingProperly() {
        assertEquals("Petteri", defaultUser.getName());
    }

    @Test
    public void toStringInUserIsWorkingProperly() {
        assertEquals("Petteri;0;0", defaultUser.toString());
    }
    
    @Test
    public void getNameModifiedinUserIsWorkingProperly() {
        User testUser = new User("tapIO   ");
        
        assertEquals("TAPIO", testUser.getNameModified());
    }
    
    @Test
    public void addingUserReturnsTrueWhenNameIsNotTaken() {
        boolean added = control.addUser("Teppo");
        
        assertEquals(true, added);
    }
    
    @Test
    public void addingUserReturnsFalseWhenNameIsTaken() {
        control.addUser("Teppo");
        boolean added = control.addUser("Teppo");
        
        assertEquals(false, added);
    }
    
    @Test
    public void addUserReturnsTrueWhenUsingUserParameterAndUserHasNotBeenAdded() {
        boolean added = control.addUser(defaultUser);
        
        assertEquals(true, added);
    }
    
    @Test
    public void addUserReturnsFalseWhenUsingUserParameterAndUserHasBeenAdded() {
        control.addUser(defaultUser);
        boolean added = control.addUser(defaultUser);
        
        assertEquals(false, added);
    }
    
    @Test
    public void userExistsReturnsFalseWhenUserHasNotBeenAdded() {
        assertEquals(false, control.userExists("test"));
    }
    
    @Test
    public void userExistsReturnsTrueWhenUserHasBeenAdded() {
        control.addUser("Teppo");
        assertEquals(true, control.userExists("Teppo"));
    }
    
    @Test
    public void userConstructorDefinesStartingLossesCorrectly() {
        assertEquals(0, defaultUser.getLosses());
    }
    
    @Test
    public void userConstructorDefinesStartingWinsCorrectly() {
        assertEquals(0, defaultUser.getWins());
    }
    
    @Test
    public void userControlGetUsersReturnsACollectionWithAllUsers() {
        control.addUser(defaultUser);
        control.addUser("Teppo");
        control.addUser("PeliMarkku");
        
        assertEquals(3, control.getUsers().size());
    }
    
    @Test 
    public void userCountWorksProperly() {
        control.addUser(defaultUser);
        control.addUser("Teppo");
        
        assertEquals(2, control.userCount());
    }
    
    @Test
    public void getUserReturnsNullWhenUserDoesNotExist() {
        
        assertEquals(null, control.getUser("Karri"));
    }
    
    
}
