package Presentation.Controllers;

import Business.Exception.BusinessException;
import Business.Exception.UserException.InvalidLoginEmailException;
import Business.Exception.UserException.InvalidPasswordException;
import Business.Exception.UserException.UserAlreadyExistsException;
import Business.Exception.UserException.UserException;
import Persistance.Exception.ConnectionErrorException;
import Presentation.MainController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterController implements ActionListener {

    private final MainController mainController;

    public RegisterController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "singUp":
                try {
                    signUp();
                } catch (ConnectionErrorException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case "login":
                mainController.swapPanel("login");
                break;

            default:
                break;
        }
    }
    private void finishSignUp(boolean wasSuccessful) {
        if (wasSuccessful) {
            mainController.swapPanel("game");
        } else {
            mainController.clearForm("register");
        }
    }

    public void signUp() throws ConnectionErrorException {
        String[] info = mainController.getRegisterInfo();
        String username = info[0];
        String email = info[1];
        String password = info[2];
        String confirmPassword = info[3];

        try {
            if (!isUsernameValid(username)) return;
            if (!isEmailValid(email)) return;
            if (!isPasswordValid(password)) return;

            mainController.registerUser(username, email, password, confirmPassword);
            finishSignUp(true);
        } catch (InvalidLoginEmailException e) {
            mainController.adviceMessage(e.getMessage(), "Wrong Email Format", "register");
            finishSignUp(false);
        } catch (InvalidPasswordException e) {
            mainController.adviceMessage(e.getMessage(), "Invalid Password", "register");
            finishSignUp(false);
        } catch (UserAlreadyExistsException e) {
            mainController.adviceMessage(e.getMessage(), "User Already Exists", "register");
            finishSignUp(false);
        } catch (UserException e) {
            mainController.adviceMessage(e.getMessage(), "Database Error", "register");
            finishSignUp(false);
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isUsernameValid(String username) throws ConnectionErrorException {
        if (!mainController.checkValidUsername(username)) {
            mainController.registerEnterValid("username");
            finishSignUp(false);
            return false;
        }
        return true;
    }

    private boolean isEmailValid(String email) {
        if (email.isEmpty()) {
            mainController.registerEnterValid("email");
            finishSignUp(false);
            return false;
        }
        if (!mainController.checkValidEmail(email)) {
            mainController.registerEnterValid("email");
            finishSignUp(false);
            return false;
        }
        return true;
    }

    private boolean isPasswordValid(String password) {
        if (password.length() < 8) {
            mainController.registerEnterValid("lengthPassword");
            finishSignUp(false);
            return false;
        }
        if (!mainController.checkLowerCaseCaracter(password)) {
            mainController.registerEnterValid("lowerCasePassword");
            finishSignUp(false);
            return false;
        }
        if (!mainController.checkUpperCaseCaracter(password)) {
            mainController.registerEnterValid("upperCasePassword");
            finishSignUp(false);
            return false;
        }
        if (!mainController.checkMinOneNumber(password)) {
            mainController.registerEnterValid("minOneNumber");
            finishSignUp(false);
            return false;
        }
        return true;
    }

}
