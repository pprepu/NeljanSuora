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
import neljansuora.dao.FileUserDao;

/**
 *
 * @author annika
 */
public class NeljanSuoraTest {
    
    User defaultUser;
    Usercontrol control;
    FileUserDao fileUserDao;
    
    @Before
    public void setUp() {
        defaultUser = new User("Petteri");
        control = new Usercontrol();
        fileUserDao = new FileUserDao("testusers.txt");
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
    public void addingUserReturnsTrueWhenNameIsNotTaken() {
        boolean added = fileUserDao.addUser("Teppo");
        
        assertEquals(true, added);
    }
    
    @Test
    public void addingUserReturnsFalseWhenNameIsTaken() {
        fileUserDao.addUser("Teppo");
        boolean added = fileUserDao.addUser("Teppo");
        
        assertEquals(false, added);
    }
    
    @Test
    public void addUserReturnsTrueWhenUsingUserParameterAndUserHasNotBeenAdded() {
        boolean added = fileUserDao.addUser(defaultUser);
        
        assertEquals(true, added);
    }
    
    @Test
    public void addUserReturnsFalseWhenUsingUserParameterAndUserHasBeenAdded() {
        fileUserDao.addUser(defaultUser);
        boolean added = fileUserDao.addUser(defaultUser);
        
        assertEquals(false, added);
    }
    
    @Test
    public void userExistsReturnsFalseWhenUserHasNotBeenAdded() {
        assertEquals(false, fileUserDao.userExists("test"));
    }
    
    @Test
    public void userExistsReturnsTrueWhenUserHasBeenAdded() {
        fileUserDao.addUser("Teppo");
        assertEquals(true, fileUserDao.userExists("Teppo"));
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
        fileUserDao.addUser(defaultUser);
        fileUserDao.addUser("Teppo");
        fileUserDao.addUser("PeliMarkku");
        
        assertEquals(3, fileUserDao.getUsers().size());
    }
    
    @Test 
    public void userCountWorksProperly() {
        fileUserDao.addUser(defaultUser);
        fileUserDao.addUser("Teppo");
        
        assertEquals(2, fileUserDao.userCount());
    }
    
    @Test
    public void getUserReturnsNullWhenUserDoesNotExist() {
        
        assertEquals(null, fileUserDao.getUser("Karri"));
    }
    
    @Test
    public void readFromFileWorksProperly() {
        fileUserDao.readFromFile();
        
        assertEquals(2, fileUserDao.userCount());
    }
    
    @Test
    public void currentUserIsNullAtFirst() {
        assertEquals(null, this.control.getCurrentUser());
    }
    
    @Test
    public void loginChangesCurrentUser() {
        control.logIn(new User("Jani"));
        assertEquals("Jani", control.getCurrentUser().getName());
    }
    
    @Test 
        public void getCurrentUserWorksProperly() {
        control.logIn(new User("Jani"));
        assertEquals("Jani;0;0", control.getCurrentUser().toString());
    }
    
    @Test
    public void logoutChangesCurrentUserToNull() {
        control.logIn(new User("Jani"));
        control.logOut();
        assertEquals(null, this.control.getCurrentUser());
    }
    
    @Test
    public void addWinsAddsAWin() {
        control.logIn(new User("Jani"));
        control.win();
        control.win();
        assertEquals(2, control.getCurrentUser().getWins());
    }
    
    @Test
    public void addLossAddsALoss() {
        control.logIn(new User("Jani"));
        control.lose();
        control.lose();
        assertEquals(2, control.getCurrentUser().getLosses());
    }
}
