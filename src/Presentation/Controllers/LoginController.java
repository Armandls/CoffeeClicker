package Presentation.Controllers;

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

    private LoginView loginView;

    public LoginController(FrameController mainController) {
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

    public void setView(LoginView view) {
        this.loginView = view;
    }

    void login() {
        //TODO: Implement login
        String[] info = loginView.getInfo();
        if (info[0].split(":")[1].equalsIgnoreCase("admin")) {
            mainController.swapPanel("game");
        }
    }
}

