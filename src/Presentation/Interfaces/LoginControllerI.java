package Presentation.Interfaces;

import Business.Exception.UserException.InvalidLoginEmailException;
import Business.Exception.UserException.InvalidPasswordException;
import Business.Exception.UserException.UserException;
import Business.Exception.UserException.UserNotFoundException;

/**
 * Interface for LoginController.
 */
public interface LoginControllerI {

    /**
     * Swaps the current panel with the specified panel.
     *
     * @param name The name of the panel to swap to.
     */
    void swapPanel(String name);

    /**
     * Clears the form with the specified name.
     *
     * @param name The name of the form to clear.
     */
    void clearForm(String name);

    /**
     * Gets the login information.
     *
     * @return The login information.
     */
    String[] getLoginInfo();

    /**
     * Enters a valid email.
     *
     * @param email The email to enter.
     */
    void loginEnterValid(String email);

    /**
     * Logs in the user.
     *
     * @throws UserException if a user error occurs.
     */
    void loginUser(String email, String password) throws UserException;

    /**
     * Shows an advice message.
     *
     * @param message The message to show.
     * @param type The type of message.
     * @param title The title of the message.
     */
    void adviceMessage(String message, String type, String title);

}
