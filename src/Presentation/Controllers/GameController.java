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
    private GameView gameView;
    private MainController mainController;


    public GameController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case "profile":
                System.out.println("profile");
                gameView.stop();
                gameView.showProfile();
                break;

            case "config":
                System.out.println("config");
                gameView.stop();
                gameView.showConfig();
                break;

            case "phone":
                System.out.println("phone");
                gameView.toggleStore();
                break;

            case "click":
                System.out.println("click");
                gameView.increase();
                gameView.startRedPanelAnimation(MouseInfo.getPointerInfo().getLocation()); // Trigger animation when click button is pressed
                break;
            case "profileClose":
                System.out.println("profileClose");
                gameView.start();
            
                gameView.hideProfile();
                break;
            case "configClose":
                System.out.println("configClose");
                gameView.start();
                gameView.hideConfig();
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
    public void initializeGame (int currency, int basicGenerator, int midGenerator, int highGenerator, int lvlBasicImp, int lvlMidImp, int lvlHighImp){
        this.gameView.initialize(currency, basicGenerator, midGenerator, highGenerator, lvlBasicImp, lvlMidImp, lvlHighImp);
    }
}
