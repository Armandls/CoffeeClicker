package Business;

import Business.Entities.User;
import Persistance.DAO.UserDAO;

/*Class to manage the different entities regarding the Game
 *  @User
 */
public class UserManager {

    private UserDAO userDAO;

    public UserManager(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void addUser(User user) {
        userDAO.addUser(user);
    }

    public User getUser(String email) {
        return userDAO.getUser(email);
    }

    public void deleteUser(String email) {
        userDAO.deleteUser(email);
    }
}
