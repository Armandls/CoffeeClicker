package Presentation.Controllers;
import Business.UserManager;
import Persistance.Exception.ConnectionErrorException;
import Presentation.FrameController;
import Presentation.MainController;
import Presentation.Views.GameView;
import Presentation.Views.LoginView;
import Presentation.Views.StoresView;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Timer;

public class GameController implements ActionListener {
    private MainController mainController;


    public GameController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case "profile":
                System.out.println("profile");
                mainController.showProfile();
                break;

            case "config":
                System.out.println("config");
                mainController.showConfig();
                break;

            case "phone":
                System.out.println("phone");
                mainController.toggleStore();
                break;

            case "click":
                System.out.println("click");
                mainController.startRedPanelAnimation(MouseInfo.getPointerInfo().getLocation());
                break;

            case "profileClose":
                System.out.println("profileClose");
                mainController.hideProfile();
                break;

            case "configClose":
                System.out.println("configClose");
                mainController.hideConfig();
                break;

            case "deleteAccount":
                System.out.println("Delete Account");
                try {
                    deleteAccount();
                } catch (ConnectionErrorException ex) {
                    throw new RuntimeException(ex);
                }
                mainController.swapPanel("login");
                break;

            case "logout":
                System.out.println("Logout");
                logout();
                mainController.swapPanel("login");
                break;
        }
    }

    void logout() {
        mainController.restartValuesUser();
    }

    void deleteAccount() throws ConnectionErrorException {
        mainController.deleteUser();
    }

}
