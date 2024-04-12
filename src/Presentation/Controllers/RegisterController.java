package Presentation.Controllers;

import Business.Entities.User;
import Business.UserManager;
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
                finishSignUp();
                break;
            case "login":
                System.out.println("Login");
                mainController.swapPanel("login");
                break;

            default:
                break;
        }
    }

    private void finishSignUp() {
    }

    public void setView(RegisterView view) {
        this.registerView = view;
    }
    private void finishSignUp(boolean wasSuccessful) {
        if (wasSuccessful) {
            mainController.swapPanel("game");
        } else {
            registerView.clearForm();
        }
    }

    public void signUp() {
        UserManager userManager = new UserManager(new SQLUserDAO());
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
                password = password.replaceAll(", ", "");
                password = password.substring(1,password.length()-1);
            } else if (entry.startsWith("confirmPassword:")) {
                confirmPassword = entry.substring("confirmPassword:".length());
                confirmPassword = confirmPassword.replaceAll(", ", "");
                confirmPassword = confirmPassword.substring(1,confirmPassword.length()-1);
            }
        }

        if (userManager.getUser(email) != null) {
            registerView.userAlreadyExists();
            finishSignUp(false);
        }

        if (!password.equals(confirmPassword)) {
            registerView.passwordDoesntMatch();
            finishSignUp(false);
        }
        userManager.addUser(new User(username, email, password));
        finishSignUp(true);
    }
}
