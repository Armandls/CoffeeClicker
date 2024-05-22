package Presentation.Controllers;

import Presentation.MainController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatisticsController implements ActionListener {

    private MainController mainController;

    public StatisticsController(MainController mainController) {
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
                //todo canviar les dades del grafic
                break;
        }
    }
}
