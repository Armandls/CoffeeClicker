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
    private UserManager userManager;
    private GameManager gameManager;

    public HomeController(MainController mainController, UserManager userManager, GameManager gameManager) {
        this.mainController = mainController;
        this.userManager = userManager;
        this.gameManager = gameManager;
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
            String gameIdStr = command.substring(8);
            int gameId = Integer.parseInt(gameIdStr);
            mainController.resumeGame(gameId);
        }
    }

    public void setView(HomeView view) {
        this.homeView = view;
        setupListeners();
    }
    private void setupListeners() {
        this.homeView.addNewGameButtonListener(this);
        this.homeView.addResumeGameButtonListener(e -> resumeGame());
    }
    public void resumeGame() {
        try {
            Map<Integer, Integer> games = gameManager.getUnfinishedGames(userManager.getCurrentUser().getEmail());
            homeView.displayGames(games);
        } catch (PersistenceException e) {
            throw new RuntimeException(e);
        }
    }
}
