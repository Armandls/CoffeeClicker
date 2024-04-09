package Presentation.Controllers;

import Presentation.FrameController;
import Presentation.MainController;
import Presentation.Views.RegisterView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                singUp();
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

    void singUp() {
        //TODO: implement singUp
    }
}
