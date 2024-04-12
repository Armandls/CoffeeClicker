package Business;

import Business.Entities.User;
import Persistance.DAO.UserDAO;
import Persistance.SQL.SQLUserDAO;

/*Class to manage the different entities regarding the Game
 *  @User
 */
public class UserManager {

    private UserDAO userDAO;

    public UserManager(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    public UserManager() {
        this.userDAO = new SQLUserDAO();
    }

    /**
     * Funció que retorna la informació del usuari que es vol buscar amb email a la base de dades separada en els seus camps
     * en un array de String.
     * @param email es el valor que utilitzem per cercar l'usuari a al base de dades.
     * @return String[3]:   pos0 -> nickname.
     *                      pos1 -> email.
     *                      pos2 -> password
     *          En el cas de que no trobi l'usuari retornara un null. Aquesta gestió es canviara amb la implementació de les excepcions.
     */
    public String[] getUserInfo(String email) {
        String[] info = new String[3];
        User user = userDAO.getUser(email);
        if (user != null) {
            info[0] = user.getNickname();
            info[1] = user.getEmail();
            info[2] = user.getPassword();
            return info;
        }
        return null;
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
    public void updateUser(User user) {userDAO.updateUser(user);}
}
