package Presentation.Controllers;
import Persistance.Exception.ConnectionErrorException;
import Persistance.Exception.PersistenceException;
import Presentation.FrameController;
import Presentation.Interfaces.GameControllerI;
import Presentation.MainController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameController implements ActionListener {
    private final GameControllerI mainController;

    public GameController(GameControllerI mainController) {
        this.mainController = mainController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case "profile":
                mainController.showProfile();
                break;

            case "config":
                mainController.showConfig();
                break;

            case "phone":
                mainController.toggleStore();
                break;

            case "click":
                mainController.startRedPanelAnimation(MouseInfo.getPointerInfo().getLocation());
                break;

            case "profileClose":
                mainController.hideProfile();
                break;

            case "configClose":
                mainController.hideConfig();
                break;

            case "deleteAccount":
                try {
                    deleteAccount();
                } catch (ConnectionErrorException ex) {
                    throw new RuntimeException(ex);
                }
                mainController.swapPanel("login");
                break;

            case "logout":
                logout();
                mainController.logout();
                break;
            case "exit":
                try {
                    mainController.exit();
                    finish(JOptionPane.showConfirmDialog(null, "Do you want to finish the game?", "Game", JOptionPane.OK_OPTION, JOptionPane.NO_OPTION));
                } catch (PersistenceException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case "statistics":
                mainController.fetchGames();
                mainController.swapPanel("statistics");
                break;
        }
    }

    void finish(int response) throws PersistenceException {
        mainController.saveGame(response == JOptionPane.OK_OPTION);
        mainController.swapPanel("home");
    }

    void logout() {
        mainController.restartValuesUser();
        mainController.restartGame();
    }

    void deleteAccount() throws ConnectionErrorException {
        mainController.deleteUser();
    }

}
