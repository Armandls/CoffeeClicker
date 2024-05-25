package Presentation.Controllers;
import Presentation.Interfaces.StatisticsControllerI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatisticsController implements ActionListener {

    private final StatisticsControllerI mainController;

    public StatisticsController(StatisticsControllerI mainController) {
        this.mainController = mainController;
    }
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
