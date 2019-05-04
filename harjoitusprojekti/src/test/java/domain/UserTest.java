
package domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import neljansuora.domain.User;

public class UserTest {
    
    User defaultUser;
    
    @Before
    public void setUp() {
        defaultUser = new User("Petteri");
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
    public void userConstructorDefinesStartingLossesCorrectly() {
        assertEquals(0, defaultUser.getLosses());
    }
    
    @Test
    public void userConstructorDefinesStartingWinsCorrectly() {
        assertEquals(0, defaultUser.getWins());
    }
    
    @Test
    public void compareToReturns0WhenUsersHaveEqualAmountOfWins() {
        User user1 = new User("Kalle", 5, 0);
        User user2 = new User("Timppa", 5, 0);
        
        assertEquals(0, user1.compareTo(user2));
    }
    
    @Test
    public void compareToReturnsNegativeIntegerWhenUserHasMoreWins() {
        User user1 = new User("Kalle", 5, 0);
        User user2 = new User("Tapsa", 3, 0);
        
        assertTrue(user1.compareTo(user2) < 0);
    }
    
    @Test
    public void compareToReturnsPositiveIntegerWhenUserHasLessWins() {
        User user1 = new User("Kalle", 5, 0);
        User user2 = new User("Tapsa", 3, 0);
        
        assertTrue(user2.compareTo(user1) > 0);
    }
}
