package Presentation.Controllers;

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
                System.out.println("back");
                mainController.swapPanel("game");
                break;
            case "logout":
                System.out.println("logout");
                break;
            case "deleteAccount":
                System.out.println("deleteAccount");
                break;
            default:
                String gameId = e.getActionCommand();
                mainController.displayGameStats(gameId);
                break;
        }
    }
}
