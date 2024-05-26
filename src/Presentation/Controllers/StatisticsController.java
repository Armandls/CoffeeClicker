package Presentation.Controllers;

import Persistance.Exception.ConnectionErrorException;
import Presentation.Interfaces.StatisticsControllerI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller class for managing statistics-related actions and interactions.
 */
public class StatisticsController implements ActionListener {

    private final StatisticsControllerI mainController;

    /**
     * Constructor for StatisticsController.
     *
     * @param mainController The main controller interface.
     */
    public StatisticsController(StatisticsControllerI mainController) {
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
            case "back":
                mainController.swapPanel("game");
                break;
            case "logout":
                logout();
                mainController.logout();
                break;
            case "deleteAccount":
                try {
                    deleteAccount();
                } catch (ConnectionErrorException ex) {
                    throw new RuntimeException(ex);
                }
                mainController.swapPanel("login");
                break;
            default:
                String gameId = e.getActionCommand();
                mainController.displayGameStats(gameId);
                break;
        }
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
