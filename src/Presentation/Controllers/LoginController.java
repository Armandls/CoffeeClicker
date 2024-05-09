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
    private LoginView loginView;
    private String email = "";

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
            loginView.clearForm();
            mainController.swapPanel("home");
        } else {
            loginView.clearForm();
        }
    }

    void login() {
        String[] info = loginView.getInfo();

        // Comprovar que els camps no estiguin buits
        if (info[0].equals( "username:")) {
            loginView.enterValidEmail();
            finishSignUp(false);
        }
        else {
            if (info[1].split(":")[1].trim().length() < 7) {
                loginView.enterValidPassword();
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
                        loginView.adviceMessage(e.getMessage(), "Wrong Email Format");
                        finishSignUp(false);
                    } catch (UserNotFoundException e) {
                        loginView.adviceMessage(e.getMessage() + " Please enter a valid email.", "Invalid email");
                        finishSignUp(false);
                    } catch (InvalidPasswordException e) {
                        loginView.adviceMessage(e.getMessage() + "Please enter a valid password", "Invalid password");
                    } catch (UserException e) {
                        loginView.adviceMessage(e.getMessage(), "Database Error");
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

