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

    public void setView(LoginView view) {
        this.loginView = view;
    }

    void login() {
        //TODO: Implement login
        String[] info = loginView.getInfo();

        //Dades introduides usuari.
        String userLoginMail = info[0].split(":")[1].trim();
        String userLoginPass = info[1].split(":")[1].trim().replaceAll(" ", "").replaceAll(",","").replace("[", "").replace("]","");
        String userLoginRem =  info[2].split(":")[1].trim();

        //Cerca d'informació
        String[] userInfo = userManager.getUserInfo(userLoginMail);
        if (userInfo != null) {
            if (userInfo[2].equals(userLoginPass)) {
                //Cas en el que s'ha loguejat correctament
                mainController.swapPanel("game");
            }
            else {
                //Cas en que la contrassenya no s'ha introduit correctament
                System.out.println("Contrasenya incorrecta");
            }
        }
        else {
            //Cas en el que no s'ha trobat l'usuari en la base de dades amb l'email proporcionat.
            System.out.println("Usuari no trobat");
        }
        /*
        if (info[0].split(":")[1].equalsIgnoreCase("admin")) {
            mainController.swapPanel("game");
        }
        */
    }
}

