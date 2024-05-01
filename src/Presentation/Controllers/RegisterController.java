package Presentation.Controllers;

import Business.Entities.User;
import Business.Exception.BusinessException;
import Business.Exception.UserException.InvalidLoginEmailException;
import Business.Exception.UserException.InvalidPasswordException;
import Business.Exception.UserException.UserAlreadyExistsException;
import Business.Exception.UserException.UserException;
import Business.UserManager;
import Persistance.DAO.UserDAO;
import Persistance.Exception.ConnectionErrorException;
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

    public void signUp() throws ConnectionErrorException{
        String[] info = registerView.getInfo();

        if (info[1].isEmpty()) {
            registerView.enterValidEmail();
            finishSignUp(false);
        }
        else {
            if (info[2].length() < 7) {
                registerView.enterValidPassword();
                finishSignUp(false);
            }
            else {
                String username = info[0];
                String email = info[1];
                String password = info[2];
                String confirmPassword = info[3];

                try {
                    userManager.registerUser(username, email, password, confirmPassword);
                    finishSignUp(true);
                } catch (InvalidLoginEmailException e) {
                    registerView.adviceMessage(e.getMessage(), "Wrong Email Format");
                    finishSignUp(false);
                } catch (InvalidPasswordException e) {
                    registerView.adviceMessage(e.getMessage(), "Invalid Password");
                    finishSignUp(false);
                } catch (UserAlreadyExistsException e) {
                    registerView.adviceMessage(e.getMessage(), "User Already Exists");
                    finishSignUp(false);
                } catch (UserException e) {
                    registerView.adviceMessage(e.getMessage(), "Database Error");
                    finishSignUp(false);
                } catch (BusinessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
