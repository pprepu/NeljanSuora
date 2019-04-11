

package neljansuora.dao;

import java.util.Collection;
import neljansuora.domain.User;


public interface UserDao {
    boolean addUser(User user);
    User getUser(String username);
    boolean userExists(String username);
    void changeUserState(User user);
    Collection<User> getUsers();

}
