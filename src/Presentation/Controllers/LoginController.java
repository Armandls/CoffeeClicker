package Presentation.Controllers;

import Business.Exception.UserException.InvalidLoginEmailException;
import Business.Exception.UserException.InvalidPasswordException;
import Business.Exception.UserException.UserException;
import Business.Exception.UserException.UserNotFoundException;
import Presentation.MainController;
import Presentation.Views.LoginView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController implements ActionListener {
    private MainController mainController;

    public LoginController(MainController mainController) {
        this.mainController = mainController;
    }

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

    private void finishSignUp(boolean wasSuccessful) {
        if (wasSuccessful) {
            mainController.clearForm("login");
            mainController.swapPanel("home");
        } else {
            mainController.clearForm("login");
        }
    }

    void login() {
        String[] info = mainController.getLoginInfo();

        // Comprovar que els camps no estiguin buits
        if (info[0].equals( "username:")) {
            mainController.loginEnterValid("email");
            finishSignUp(false);
        }
        else {
            if (info[1].split(":")[1].trim().length() < 7) {
                mainController.loginEnterValid("password");
                finishSignUp(false);
            }
            else {
                //Dades introduides usuari.
                String userLoginMail = info[0].split(":")[1].trim();
                String userLoginPass = info[1].split(":")[1].trim().replaceAll(" ", "").replaceAll(",","").replace("[", "").replace("]","");
                String userLoginRem =  info[2].split(":")[1].trim();
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

                /*
                if (info[0].split(":")[1].equalsIgnoreCase("admin")) {
                    mainController.swapPanel("game");
                }
                */
            }
        }
    }
}

