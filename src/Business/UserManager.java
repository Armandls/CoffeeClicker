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

/* Class to manage the different entities regarding the User */
public class UserManager {

    private final UserDAO userDAO;
    private User user;

    /**
     * Constructs a new UserManager with the specified UserDAO.
     * @param userDAO The UserDAO to be used.
     */
    public UserManager(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Adds a user to the database.
     * @param user The user to be added.
     * @throws ConnectionErrorException If there is an error connecting to the database.
     */
    public void addUser(User user) throws ConnectionErrorException {
        userDAO.addUser(user);
    }

    /**
     * Retrieves the user with the specified email from the database.
     * @param email The email of the user to retrieve.
     * @return The user.
     * @throws BusinessException If an error occurs during the retrieval process.
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
     * Retrieves the currently logged-in user.
     * @return The currently logged-in user.
     */
    public User getCurrentUser() {
        return user;
    }

    /**
     * Deletes the currently logged-in user from the database.
     * @throws ConnectionErrorException If there is an error connecting to the database.
     */
    public void deleteUser() throws ConnectionErrorException {
        userDAO.deleteUser(user.getEmail());
        restartValuesUser();
    }

    /**
     * Resets the user values.
     */
    public void restartValuesUser() {
        user.setNickname("null");
        user.setEmail("null");
        user.setPassword("null");
    }

    /**
     * Updates the specified user in the database.
     * @param user The user to be updated.
     * @throws ConnectionErrorException If there is an error connecting to the database.
     */
    public void updateUser(User user) throws ConnectionErrorException {
        userDAO.updateUser(user);
    }

    /**
     * Checks if the password entered by the user in the login form is correct.
     * @param checkedPassword The password entered by the user.
     * @param user The user who logged in.
     * @return True if the password is correct, false otherwise.
     */
    private boolean checkPassword(String checkedPassword, User user) {
        return user.getPassword().equals(checkedPassword);
    }

    /**
     * Checks if the repeated password matches the original password.
     * @param checkedPassword The repeated password.
     * @param userPassword The original password.
     * @return True if the repeated password matches the original password, false otherwise.
     */
    private boolean checkRepeatPassword(String checkedPassword, String userPassword) {
        return userPassword.equals(checkedPassword);
    }

    /**
     * Checks if the password contains at least one lowercase character.
     * @param password The password to check.
     * @return True if the password contains at least one lowercase character, false otherwise.
     */
    public boolean checkLowerCaseCaracter(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isLowerCase(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the password contains at least one uppercase character.
     * @param password The password to check.
     * @return True if the password contains at least one uppercase character, false otherwise.
     */
    public boolean checkUpperCaseCaracter(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the password contains at least one digit.
     * @param password The password to check.
     * @return True if the password contains at least one digit, false otherwise.
     */
    public boolean checkMinOneNumber(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the username is available.
     * @param username The username to check.
     * @return True if the username is available, false otherwise.
     * @throws ConnectionErrorException If there is an error connecting to the database.
     */
    public boolean checkValidUsername(String username) throws ConnectionErrorException {
        return userDAO.isUsernameAvailable(username);
    }

    /**
     * Checks if the email is in a valid format.
     * @param email The email to check.
     * @return True if the email is in a valid format, false otherwise.
     */
    public boolean checkValidEmail(String email) {
        return email.contains("@gmail.com");
    }

    /**
     * Logs in a user with the specified email and password.
     * @param userLoginMail The email of the user.
     * @param userLoginPassword The password of the user.
     * @throws UserException If an error occurs during the login process.
     */
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
                this.user = new User(username, userMail, userPassword);
            }
        }
    }


}
