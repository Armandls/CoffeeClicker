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

    public void addUser(User user, int id_game) {
        userDAO.addUser(user, id_game);
    }
}
