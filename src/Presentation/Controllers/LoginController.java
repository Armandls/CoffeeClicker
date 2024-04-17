package Presentation.Controllers;

import Business.UserManager;
import Presentation.FrameController;
import Presentation.MainController;
import Presentation.Views.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoginController implements ActionListener {

    private FrameController mainController;
    private UserManager userManager;
    private LoginView loginView;

    public LoginController(FrameController mainController) {
        this.mainController = mainController;
        this.userManager = new UserManager();

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
            mainController.swapPanel("game");
        } else {
            loginView.clearForm();
        }
    }

    public void setView(LoginView view) {
        this.loginView = view;
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

                if (!info[0].contains("@gmail.com")) {
                    loginView.enterValidEmail();
                    finishSignUp(false);
                }
                else {
                    if (userManager.getUser(userLoginMail) != null) {
                        if (userManager.checkPassword(userLoginPass, userLoginMail)) {
                            //Cas en el que s'ha loguejat correctament
                            finishSignUp(true);
                        }
                        else {
                            //Cas en que la contrassenya no s'ha introduit correctament
                            loginView.passwordDoesntMatch();
                            finishSignUp(false);
                        }
                    }
                    else {
                        //Cas en el que no s'ha trobat l'usuari en la base de dades amb l'email proporcionat.
                        loginView.userDoesntExist();
                        finishSignUp(false);
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
}

