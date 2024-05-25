package Presentation.Controllers;

import Business.Exception.UserException.InvalidLoginEmailException;
import Business.Exception.UserException.InvalidPasswordException;
import Business.Exception.UserException.UserException;
import Business.Exception.UserException.UserNotFoundException;
import Presentation.Interfaces.LoginControllerI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller class for managing login-related actions and interactions.
 */
public class LoginController implements ActionListener {
    private final LoginControllerI mainController;

    /**
     * Constructor for LoginController.
     *
     * @param mainController The main controller interface.
     */
    public LoginController(LoginControllerI mainController) {
        this.mainController = mainController;
    }

    /**
     * Handles action events triggered by GUI components.
     *
     * @param e The action event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "login":
                System.out.println("Login");
                login();
                break;

            case "register":
                System.out.println("Register");
                mainController.swapPanel("register");
                break;

            case "forgotPassword":
                System.out.println("Forgot Password");
                break;

            default:
                break;
        }
    }

    /**
     * Handles the login process.
     */
    private void login() {
        String[] info = mainController.getLoginInfo();

        // Check if the fields are empty
        if (info[0].equals("username:")) {
            mainController.loginEnterValid("email");
            finishSignUp(false);
        } else {
            if (info[1].split(":")[1].trim().length() < 7) {
                mainController.loginEnterValid("password");
                finishSignUp(false);
            } else {
                // User input data
                String userLoginMail = info[0].split(":")[1].trim();
                String userLoginPass = info[1].split(":")[1].trim().replaceAll(" ", "").replaceAll(",", "").replace("[", "").replace("]", "");
                try {
                    mainController.loginUser(userLoginMail, userLoginPass);
                    finishSignUp(true);
                } catch (InvalidLoginEmailException e) {
                    mainController.adviceMessage(e.getMessage(), "Wrong Email Format", "login");
                    finishSignUp(false);
                } catch (UserNotFoundException e) {
                    mainController.adviceMessage(e.getMessage() + " Please enter a valid email.", "Invalid email", "login");
                    finishSignUp(false);
                } catch (InvalidPasswordException e) {
                    mainController.adviceMessage(e.getMessage() + "Please enter a valid password", "Invalid password", "login");
                } catch (UserException e) {
                    mainController.adviceMessage(e.getMessage(), "Database Error", "login");
                }
            }
        }
    }

    /**
     * Handles the completion of the sign-up process.
     *
     * @param wasSuccessful Indicates whether the sign-up process was successful or not.
     */
    private void finishSignUp(boolean wasSuccessful) {
        if (wasSuccessful) {
            mainController.clearForm("login");
            mainController.swapPanel("home");
        } else {
            mainController.clearForm("login");
        }
    }
}
