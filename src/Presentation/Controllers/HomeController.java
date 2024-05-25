package Presentation.Controllers;

import Business.Exception.BusinessException;
import Persistance.Exception.PersistenceException;
import Presentation.FrameController;
import Presentation.Interfaces.HomeControllerI;
import Presentation.MainController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeController implements ActionListener{
    private final HomeControllerI mainController;

    public HomeController(HomeControllerI mainController) {
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
