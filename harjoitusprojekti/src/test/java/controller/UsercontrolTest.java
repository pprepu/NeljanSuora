/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import neljansuora.controller.Usercontrol;
import neljansuora.dao.FileUserDao;
import neljansuora.domain.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author annika
 */
public class UsercontrolTest {
    
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
