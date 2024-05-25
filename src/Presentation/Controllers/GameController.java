package Presentation.Controllers;

import Persistance.Exception.ConnectionErrorException;
import Persistance.Exception.PersistenceException;
import Presentation.Interfaces.GameControllerI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Controller class for managing game-related actions and interactions.
 */
public class GameController implements ActionListener {
    private final GameControllerI mainController;

    /**
     * Constructor for GameController.
     *
     * @param mainController The main controller interface.
     */
    public GameController(GameControllerI mainController) {
        this.mainController = mainController;
    }

    /**
     * Handles action events triggered by GUI components.
     *
     * @param e The action event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
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

    /**
     * Handles the completion of the game.
     *
     * @param response The user's response to finishing the game.
     * @throws PersistenceException If an error occurs during persistence operations.
     */
    void finish(int response) throws PersistenceException {
        mainController.saveGame(response == JOptionPane.OK_OPTION);
        mainController.swapPanel("home");
    }

    /**
     * Logs out the current user.
     */
    void logout() {
        mainController.restartValuesUser();
        mainController.restartGame();
    }

    /**
     * Deletes the current user's account.
     *
     * @throws ConnectionErrorException If there is an error connecting to the database.
     */
    void deleteAccount() throws ConnectionErrorException {
        mainController.deleteUser();
    }
}
