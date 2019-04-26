/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

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
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.Assert;

/**
 *
 * @author annika
 */
public class FileUserDaoTest {
    
    User defaultUser;
    Usercontrol control;
    FileUserDao fileUserDao;
    
    @Before
    public void setUp() {
        defaultUser = new User("Petteri");
        fileUserDao = new FileUserDao("testusers.txt");
        fileUserDao.saveToFile();
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
    public void addUserWithAllThreeParameterWorks() {
        fileUserDao.addUser("Voittaja", 100, 1);
        assertEquals(fileUserDao.getUser("Voittaja").toString(), "Voittaja;100;1");
    }
    
    @Test
    public void fileUserDaoGetUsersReturnsACollectionWithAllUsers() {
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
    public void changeUserStateDoesNotAddNewUsers() {
        fileUserDao.addUser("Janne");
        fileUserDao.changeUserState(new User ("newUser"));
        assertEquals(1, fileUserDao.getUsers().size());
    }
    
    @Test
    public void changeUserStateChangesExistingUserValues() {
        fileUserDao.addUser("Timo");
        fileUserDao.changeUserState(new User ("Timo", 100, 5));
        assertEquals("Timo;100;5", fileUserDao.getUser("Timo").toString());
    }
    
    @Test
    public void savingAndReadingFromFileWorksWithValidFilename() {
        fileUserDao.addUser("Test");
        fileUserDao.saveToFile();
        
        FileUserDao newFileUserDao = new FileUserDao("testusers.txt");
        newFileUserDao.readFromFile();
        assertEquals(newFileUserDao.userCount(), 1);
    }
    /*
    @Test(expected = IOException.class)
    public void throwsException() { // will pass
        FileUserDao newFileUserDao = new FileUserDao("notAnExistingFile");
        
        newFileUserDao.readFromFile();
    }
    */
}
