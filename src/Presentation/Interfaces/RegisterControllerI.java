package Presentation.Interfaces;

import Business.Exception.BusinessException;
import Persistance.Exception.ConnectionErrorException;

/**
 * Interface for RegisterController.
 */
public interface RegisterControllerI {

    /**
     * Swaps the current panel with the specified panel.
     *
     * @param panel The panel to swap to.
     */
    void swapPanel(String panel);

    /**
     * Clears the specified form.
     *
     * @param form The form to clear.
     */
    void clearForm(String form);

    /**
     * Gets the information from the register form.
     *
     * @return The information from the register form.
     */
    String[] getRegisterInfo();

    /**
     * Registers a user with the specified information.
     *
     * @param username The username of the user.
     * @param email The email of the user.
     * @param password The password of the user.
     * @param confirmPassword The confirmation password of the user.
     * @throws BusinessException If there is a business-related exception.
     * @throws ConnectionErrorException If there is a connection error.
     */
    void registerUser(String username, String email, String password, String confirmPassword) throws BusinessException, ConnectionErrorException;

    /**
     * Displays an advice message.
     *
     * @param message The message to display.
     * @param type The type of message.
     * @param title The title of the message.
     */
    void adviceMessage(String message, String type, String title);

    /**
     * Checks if the username is valid.
     *
     * @param username The username to check.
     * @return True if the username is valid, false otherwise.
     * @throws ConnectionErrorException If there is a connection error.
     */
    boolean checkValidUsername(String username) throws ConnectionErrorException;

    /**
     * Notifies the user to enter a valid username.
     *
     * @param username The username to notify about.
     */
    void registerEnterValid(String username);

    /**
     * Checks if the email is valid.
     *
     * @param email The email to check.
     * @return True if the email is valid, false otherwise.
     */
    boolean checkValidEmail(String email);

    /**
     * Checks if the password contains at least one lowercase character.
     *
     * @param password The password to check.
     * @return True if the password contains at least one lowercase character, false otherwise.
     */
    boolean checkLowerCaseCaracter(String password);

    /**
     * Checks if the password contains at least one uppercase character.
     *
     * @param password The password to check.
     * @return True if the password contains at least one uppercase character, false otherwise.
     */
    boolean checkUpperCaseCaracter(String password);

    /**
     * Checks if the password contains at least one number.
     *
     * @param password The password to check.
     * @return True if the password contains at least one number, false otherwise.
     */
    boolean checkMinOneNumber(String password);

    /**
     * Creates new game.
     */
    void createNewGame();
}
