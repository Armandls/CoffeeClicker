package Presentation;

import Business.GameManager;
import Business.GeneratorManager;
import Business.ImprovementManager;
import Business.UserManager;
import Presentation.Controllers.GameController;
import Presentation.Controllers.HomeController;
import Presentation.Controllers.LoginController;
import Presentation.Controllers.RegisterController;
import Presentation.Views.*;
import Presentation.Controllers.StoresController;
import Presentation.Views.*;

import java.io.IOException;
import java.util.Hashtable;

/*Class to manage the interactions between the user interface (UI, the View) and the Manager classes*/
public class MainController implements FrameController {

    private GameManager gameManager;
    private GeneratorManager generatorManager;
    private ImprovementManager improvementManager;
    private UserManager userManager;
    private MainFrame mainFrame;
    private MyView currentView;
    private Hashtable<String, MyView> views;
    LoginController loginController;
    RegisterController registerController;
    GameController gameController;
    StoresController storesController;
    HomeController  homeController;

    public MainController(GameManager gameManager, GeneratorManager generatorManager, ImprovementManager improvementManager, UserManager userManager) throws IOException {
        this.gameManager = gameManager;
        this.generatorManager = generatorManager;
        this.improvementManager = improvementManager;
        this.userManager = userManager;
        init();
    }

    @Override
    public void swapPanel(String panelName) {
        mainFrame.showPanel(panelName);
        currentView = views.get(panelName);
    }

    @Override
    public void initializeGame(int currency, int basicGenerator, int midGenerator, int highGenerator, int lvlBasicImp, int lvlMidImp, int lvlHighImp) {
        gameController.initializeGame(currency, basicGenerator, midGenerator, highGenerator, lvlBasicImp, lvlMidImp, lvlHighImp);
    }

    void init() throws IOException {
        mainFrame = new MainFrame();
        views = new Hashtable<>();

        loginController = new LoginController(this, userManager);
        LoginView loginView = new LoginView(loginController);
        loginController.setView(loginView);

        registerController = new RegisterController(this, userManager);
        RegisterView registerView = new RegisterView(registerController);
        registerController.setView(registerView);

        storesController = new StoresController();
        StoresView storesView = new StoresView(storesController);
        storesController.addView(storesView);

        gameController = new GameController(this, userManager);
        GameView gameView = new GameView(gameController, storesView, 0);
        gameController.setView(gameView);

        homeController = new HomeController(this, userManager, gameManager);
        HomeView homeView = new HomeView(homeController);
        homeController.setView(homeView);



        //.addPanel(gameView, "game");
        mainFrame.addPanel(loginView, "login");
        mainFrame.addPanel(registerView, "register");
        mainFrame.addPanel(gameView, "game");
        mainFrame.addPanel(homeView, "home");
        mainFrame.setVisible(true);

        views.put("login", loginView);
        views.put("register", registerView);
        views.put("game", gameView);
        views.put("home", homeView);

        currentView = loginView;
    }
}