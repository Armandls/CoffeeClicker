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
                System.out.println("Sign Up");
                try {
                    signUp();
                } catch (ConnectionErrorException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case "login":
                System.out.println("Login");
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

    public void signUp() throws ConnectionErrorException{
        String[] info = mainController.getRegisterInfo();

        if (!mainController.checkValidUsername(info[0])) {
            mainController.registerEnterValid("username");
            finishSignUp(false);
        }
        else {
            if (info[1].isEmpty()) {
                mainController.registerEnterValid("email");
                finishSignUp(false);
            }
            else {
                if (!mainController.checkValidEmail(info[1])) {
                    mainController.registerEnterValid("email");
                    finishSignUp(false);
                }
                else {
                    if (info[2].length() < 8) {
                        mainController.registerEnterValid("lengthPassword");
                        finishSignUp(false);
                    }
                    else {
                        if (!mainController.checkLowerCaseCaracter(info[2])) {
                            mainController.registerEnterValid("lowerCasePassword");
                            finishSignUp(false);
                        }
                        else {
                            if (!mainController.checkUpperCaseCaracter(info[2])) {
                                mainController.registerEnterValid("upperCasePassword");
                                finishSignUp(false);
                            }
                            else {
                                if (!mainController.checkMinOneNumber(info[2])) {
                                    mainController.registerEnterValid("minOneNumber");
                                    finishSignUp(false);
                                }
                                else {
                                    String username = info[0];
                                    String email = info[1];
                                    String password = info[2];
                                    String confirmPassword = info[3];

                                    try {
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
                            }
                        }
                    }
                }
            }
        }
    }
}
