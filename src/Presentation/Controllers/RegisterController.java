package Presentation.Controllers;

import Business.Entities.User;
import Persistance.DAO.UserDAO;
import Persistance.SQL.SQLUserDAO;
import Presentation.FrameController;
import Presentation.MainController;
import Presentation.Views.RegisterView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class RegisterController implements ActionListener {

    private RegisterView registerView;
    private FrameController mainController;

    public RegisterController(FrameController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "singUp":
                System.out.println("Sing Up");
                signUp();
                break;
            case "login":
                System.out.println("Login");
                mainController.swapPanel("login");
                break;

            default:
                break;
        }
    }

    public void setView(RegisterView view) {
        this.registerView = view;
    }

    public void signUp() {
        UserDAO userDAO = new SQLUserDAO();
        String[] info = registerView.getInfo();

        String username = "";
        String email = "";
        String password = "";
        String confirmPassword = "";

        for (String entry : info) {
            if (entry.startsWith("username:")) {
                username = entry.substring("username:".length());
            } else if (entry.startsWith("email:")) {
                email = entry.substring("email:".length());
            } else if (entry.startsWith("password:")) {
                password = entry.substring("password:".length());
                password = password.replaceAll("[\\[\\]]", "");
            } else if (entry.startsWith("confirmPassword:")) {
                confirmPassword = entry.substring("confirmPassword:".length());
                confirmPassword = confirmPassword.replaceAll("[\\[\\]]", "");
            }
        }
        if (userDAO.getUser(email) != null) {
            registerView.userAlreadyExists();
        }else {
            if (!password.equals(confirmPassword)) {
                registerView.passwordDoesntMatch();
            }else {
                userDAO.addUser(new User(username, email, password));
            }
        }
    }

}
