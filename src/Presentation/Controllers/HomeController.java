package Presentation.Controllers;

import Business.Exception.BusinessException;
import Business.Exception.UserException.InvalidLoginEmailException;
import Business.Exception.UserException.InvalidPasswordException;
import Business.Exception.UserException.UserAlreadyExistsException;
import Business.Exception.UserException.UserException;
import Business.GameManager;
import Business.UserManager;
import Persistance.Exception.ConnectionErrorException;
import Persistance.Exception.PersistenceException;
import Presentation.FrameController;
import Presentation.MainController;
import Presentation.Views.HomeView;
import Presentation.Views.RegisterView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class HomeController implements ActionListener{
    private HomeView homeView;
    private MainController mainController;

    public HomeController(MainController mainController) {
        this.mainController = mainController;
        setupListeners();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("newGame")) {
            System.out.println("New Game");
            mainController.swapPanel("game");
        } else if (command.equals("resumeGame")) {
            System.out.println("Resume Game");
            resumeGame();
        } else if (command.startsWith("loadGame-")) {
            String gameIdStr = command.substring(9);
            int gameId = Integer.parseInt(gameIdStr);
            mainController.resumeGame(gameId);
        }
    }
    private void setupListeners() {
        this.homeView.addNewGameButtonListener(this);
        this.homeView.addResumeGameButtonListener(e -> resumeGame());
    }
    public void resumeGame() {
        Map<Integer, Integer> games = mainController.getUnfinishedGames();
        homeView.displayGames(games);
    }
}
