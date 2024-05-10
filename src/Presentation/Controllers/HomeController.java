package Presentation.Controllers;

import Persistance.Exception.PersistenceException;
import Presentation.MainController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeController implements ActionListener{
    private MainController mainController;

    public HomeController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("newGame")) {
            try {
                addGame();
            } catch (PersistenceException ex) {
                throw new RuntimeException(ex);
            }
            mainController.swapPanel("game");
        } else if (command.equals("resumeGame")) {
            System.out.println("Resume Game");
            try {
                mainController.resumeGameButton();
            } catch (PersistenceException ex) {
                throw new RuntimeException(ex);
            }
        } else if (command.startsWith("loadGame-")) {
            String gameIdStr = command.substring(9);
            int gameId = Integer.parseInt(gameIdStr);
            mainController.resumeGame(gameId, userManager.getCurrentUser().getEmail());
        }
    }

    public void addGame () throws PersistenceException {
        String email = mainController.getEmail_id();
        mainController.addGame(1, 0, false, email);
    }
}
