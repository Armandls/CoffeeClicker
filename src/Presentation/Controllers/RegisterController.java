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
    private UserManager userManager;

    public RegisterController(FrameController mainController, UserManager userManager) {
        this.mainController = mainController;
        this.userManager = userManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "singUp":
                System.out.println("Sign Up");
                signUp();
                //finishSignUp();
                break;
            case "login":
                System.out.println("Login");
                mainController.swapPanel("login");
                break;

            default:
                break;
        }
    }

    /*private void finishSignUp() {
    }*/

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
        String[] info = registerView.getInfo();

        if (info[1].isEmpty()) {
            registerView.enterValidEmail();
            finishSignUp(false);
        } else {
            if (info[2].length() < 7) {
                registerView.enterValidPassword();
                finishSignUp(false);
            } else {
                String username = info[0];
                String email = info[1];
                String password = info[2];
                String confirmPassword = info[3];

                if (!email.contains("@gmail.com")) {
                    registerView.enterValidEmail();
                    finishSignUp(false);
                }
                else {
                    if (userManager.getUser(email) == null) {
                        if (password.equals(confirmPassword)) {
                            //Cas en el que s'ha loguejat correctament
                            userManager.addUser(new User(username, email, password));
                            finishSignUp(true);
                        } else {
                            //Cas en que la contrassenya sigui diferent a la de confirmació.
                            registerView.passwordDoesntMatch();
                            finishSignUp(false);
                        }
                    } else {
                        //Cas en el que s'ha trobat l'usuari en la base de dades amb l'email proporcionat.
                        registerView.userAlreadyExists();
                        finishSignUp(false);
                    }
                }
            }
        }
    }
}
