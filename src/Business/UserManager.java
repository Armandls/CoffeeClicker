package Business;

import Business.Entities.User;
import Business.Exception.BusinessException;
import Business.Exception.DataDoesntMatchException;
import Business.Exception.UserException.UserAlreadyExistsException;
import Business.Exception.UserException.*;
import Persistance.DAO.UserDAO;
import Persistance.Exception.ConnectionErrorException;
import Persistance.Exception.NotFoundException;
import Persistance.Exception.PersistenceException;
import Persistance.SQL.SQLUserDAO;

/*Class to manage the different entities regarding the Game
 *  @User
 */
public class UserManager {

    private UserDAO userDAO;
    private User user;

    public UserManager(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    public UserManager() {
        this.userDAO = new SQLUserDAO();
    }

    /*
    /**
     * Funció que retorna la informació del usuari que es vol buscar amb email a la base de dades separada en els seus camps
     * en un array de String.
     * @param email es el valor que utilitzem per cercar l'usuari a al base de dades.
     * @return String[3]:   pos0 -> nickname.
     *                      pos1 -> email.
     *                      pos2 -> password
     *          En el cas de que no trobi l'usuari retornara un null. Aquesta gestió es canviara amb la implementació de les excepcions.

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
    }*/


    /**
     * Funció que afegeix un usuari a la base de dades.
     * @param user és l'usuari que es vol afegir a la base de dades.
     */
    public void addUser(User user) throws ConnectionErrorException {
        userDAO.addUser(user);
    }


    /**
     * Funció que retorna l'usuari que es vol buscar a la base de dades.
     * @param email és el valor que utilitzem per cercar l'usuari a la base de dades.
     * @return User: l'usuari que es vol buscar.
     */
    public User getUser(String email) throws BusinessException{
        try {
            return userDAO.getUser(email);
        }
        catch (NotFoundException e) {
            throw new DataDoesntMatchException(e.getMessage());
        }
        catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }

    }

    /**
     * Funció que retorna l'usuari amb la sessió iniciada.
     * @return User: l'usuari que actualment té la sessió iniciada.
     */
    public User getCurrentUser() {
        return user;
    }




    /**
     * Funció que elimina un usuari de la base de dades.
     * @throws ConnectionErrorException si hi ha un error de connexió amb la base de dades.
     */
    public void deleteUser() throws ConnectionErrorException {
        userDAO.deleteUser(user.getEmail());
        restartValuesUser();
    }

    public void restartValuesUser() {
        user.setNickname("null");
        user.setEmail("null");
        user.setPassword("null");
    }


    /**
     * Funció que actualitza un usuari de la base de dades.
     * @param user és l'usuari que es vol actualitzar a la base de dades.
     */
    public void updateUser(User user) throws ConnectionErrorException {
        userDAO.updateUser(user);
    }

    /**
     * Funció que comprova si la contrasenya introduida per l'usuari en el login form és correcta.
     * @param checkedPassword és la contrasenya que l'usuari ha introduit.
     * @param user és l'usuari que s'ha loguejat.
     * @return boolean: true si la contrasenya és correcta, false en cas contrari.
     */
    private boolean checkPassword(String checkedPassword, User user) {
        return user.getPassword().equals(checkedPassword);
    }

    private boolean checkRepeatPassword(String checkedPassword, String userPassword) {
        return userPassword.equals(checkedPassword);
    }

    public boolean checkLowerCaseCaracter(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isLowerCase(c)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkUpperCaseCaracter(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkMinOneNumber(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkValidEmail(String email) {
        return email.contains("@gmail.com");
    }

    public void loginUser(String userLoginMail, String userLoginPassword) throws UserException {

        if (!userLoginMail.contains("@gmail.com")) {
            throw new InvalidLoginEmailException("Invalid email format. The email must contain <@gmail.com>.");
        }

        User user;
        try {
            user = userDAO.getUser(userLoginMail);
        } catch (NotFoundException e) {
            throw new UserNotFoundException("User with email <" + userLoginMail + "> was not found in the database.");
        } catch (PersistenceException e) {
            throw new UserDatabaseError(e.getMessage());
        }

        if (checkPassword(userLoginPassword, user)) {
            this.user = new User(user.getNickname(), user.getEmail(), user.getPassword());
        }
        else {
            throw new InvalidPasswordException("The password introduced for user with email <" + userLoginMail + "> does not match.");
        }
    }

    public void registerUser(String username, String userMail, String userPassword, String repeatPassword) throws BusinessException, ConnectionErrorException {


        try {
            User user = userDAO.getUser(userMail);
            throw new UserAlreadyExistsException("User with email <" + userMail + "> already exists in the database.");
        } catch (ConnectionErrorException e) {
            throw new BusinessException("Error connecting to the database.");
        } catch (PersistenceException e) {
            if (!checkRepeatPassword(userPassword, repeatPassword)) {
                throw new InvalidPasswordException("The password introduced for user with email <" + userMail + "> does not match.");
            } else {
                addUser(new User(username, userMail, userPassword));
            }
        }
    }


}
