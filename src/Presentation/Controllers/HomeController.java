package Presentation.Controllers;

import Business.Exception.BusinessException;
import Persistance.Exception.PersistenceException;
import Presentation.MainController;
import Presentation.Views.HomeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class HomeController implements ActionListener{
    private final MainController mainController;

    public HomeController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("newGame")) {
            try {
                mainController.createNewGame();
            } catch (PersistenceException ex) {
                throw new RuntimeException(ex);
            }
            mainController.swapPanel("game");
        } else if (command.equals("resumeGame")) {
            System.out.println("Resume Game");
            try {
                mainController.resumeGameButton();
            } catch (PersistenceException ex) {
                JOptionPane.showMessageDialog(null, "There are no games to load.", "Games Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (command.startsWith("loadGame-")) {
            String gameIdStr = command.substring(9);
            int gameId = Integer.parseInt(gameIdStr);
            try {
                mainController.resumeGame(gameId);
            } catch (BusinessException ex) {
                throw new RuntimeException(ex);
            }
            mainController.swapPanel("game");
        }
    }
}
