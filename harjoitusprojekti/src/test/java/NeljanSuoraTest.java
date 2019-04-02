/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import NeljanSuora.logic.Usercontrol;
import NeljanSuora.domain.User;

/**
 *
 * @author annika
 */
public class NeljanSuoraTest {
    
    User defaultUser;
    
    @Before
    public void setUp() {
        defaultUser = new User("Petteri");
    }
    
    
    @Test
    public void getNameIsWorkingProperly() {
        assertEquals("Petteri", defaultUser.getName());
    }

    @Test
    public void toStringIsWorkingProperly() {
        assertEquals("Petteri", defaultUser.toString());
    }
    
    @Test
    public void getNameModifiedIsWorkingProperly() {
        User testUser = new User("tapIO   ");
        
        assertEquals("TAPIO", testUser.getNameModified());
    }
}
