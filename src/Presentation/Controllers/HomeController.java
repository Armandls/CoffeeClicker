package Presentation.Controllers;

import Persistance.Exception.PersistenceException;
import Presentation.MainController;
import Presentation.Views.HomeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

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
            mainController.resumeGame(gameId);
            mainController.swapPanel("game");
        }
    }

    public void addGame () throws PersistenceException {
        String email = mainController.getEmail_id();
        mainController.addGame(1, 0, false, email);
    }

    /*private void setupListeners() {
        .addNewGameButtonListener(this);
        this.homeView.addResumeGameButtonListener(e -> resumeGame());
    }*/
    public void resumeGame() {
        /*try {
            Map<Integer, Integer> games = gameManager.getUnfinishedGames(userManager.getCurrentUser().getEmail());
            homeView.displayGames(games);
        } catch (PersistenceException e) {
            throw new RuntimeException(e);
        }*/
    }
}
