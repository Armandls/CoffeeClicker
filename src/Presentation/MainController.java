package Presentation;

import Business.Exception.BusinessException;
import Business.Exception.GeneratorException.GeneratorAddedException;
import Business.GameManager;
import Business.GeneratorManager;
import Business.ImprovementManager;
import Business.UserManager;
import Persistance.Exception.NotFoundException;
import Presentation.Controllers.GameController;
import Presentation.Controllers.HomeController;
import Presentation.Controllers.LoginController;
import Presentation.Controllers.RegisterController;
import Presentation.Views.*;
import Presentation.Controllers.StoresController;
import Presentation.Views.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

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



        //mainFrame.addPanel(loginView, "login");
        mainFrame.addPanel(gameView, "game");
        mainFrame.addPanel(registerView, "register");
        //mainFrame.addPanel(gameView, "game");
        mainFrame.addPanel(homeView, "home");
        mainFrame.setVisible(true);

        views.put("login", loginView);
        views.put("register", registerView);
        views.put("game", gameView);
        views.put("home", homeView);

        currentView = loginView;
    }

    public void resumeGame(int gameId) {
        int n_currencies = 0;
        int[] n_generators = {0,0,0}; //{n_basic, n_mid, n_high}
        int[] boosts_lvl = {0,0,0};   //{lvl_basic, lvl_mid, lvl_high}
        List<String> generator_types;
        int i = 0;

        try {
            n_currencies = gameManager.getGameCurrencies(gameId);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            generator_types = generatorManager.getGeneratorsTypes(gameId);
            for(String s: generator_types){
                n_generators[i] = generatorManager.getNumberOfGenerators(gameId, s);
                boosts_lvl[i++] = generatorManager.getLevelOfGenerator(gameId, s);
            }
        } catch (BusinessException e) {
            throw new RuntimeException(e); // no generators exception/persistance exception
        }
        gameController.initializeGame(n_currencies, n_generators[0], n_generators[1], n_generators[2], boosts_lvl[0],boosts_lvl[1],boosts_lvl[2]);
    }

    public void buyGenerator(String type) {
        boolean validPurchase;
        validPurchase = gameManager.buyGenerator(type);
        if (validPurchase) {
            getGeneratorInfo();
        }
        else{
            //Mostra missatge de que no es te suficient diners per comprar;
        }
    }

    public void getGeneratorInfo() {
        //Pillar info dels generadors per passar.

        //StoresView.loadShop();
    }


}