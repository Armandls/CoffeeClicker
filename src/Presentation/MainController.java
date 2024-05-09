package Presentation;

import Business.Exception.BusinessException;
import Business.Exception.GeneratorException.GeneratorAddedException;
import Business.Exception.UserException.UserException;
import Business.GameManager;
import Business.GeneratorManager;
import Business.ImprovementManager;
import Business.UserManager;
import Persistance.Exception.ConnectionErrorException;
import Persistance.Exception.NotFoundException;
import Persistance.Exception.PersistenceException;
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
import java.util.Map;

/*Class to manage the interactions between the user interface (UI, the View) and the Manager classes*/
public class MainController implements FrameController {

    private GameManager gameManager;
    private GeneratorManager generatorManager;
    private ImprovementManager improvementManager;
    private UserManager userManager;
    private MainFrame mainFrame;
    private MyView currentView;
    private Hashtable<String, MyView> views;
    private LoginView loginView;
    private RegisterView registerView;
    private StoresView storesView;
    private HomeView homeView;
    private GameView gameView;


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
        gameView.initialize(currency, basicGenerator, midGenerator, highGenerator, lvlBasicImp, lvlMidImp, lvlHighImp);
    }

    void init() throws IOException {
        mainFrame = new MainFrame();
        views = new Hashtable<>();

        LoginController loginController = new LoginController(this);
        loginView = new LoginView(loginController);

        RegisterController registerController = new RegisterController(this);
        registerView = new RegisterView(registerController);

        StoresController storesController = new StoresController();
        storesView = new StoresView(storesController);

        GameController gameController = new GameController(this);
        gameView = new GameView(gameController, storesView, 0);

        HomeController homeController = new HomeController(this);
        homeView = new HomeView(homeController);
        homeView.addNewGameButtonListener(homeController);
        homeView.addResumeGameButtonListener(e -> {
            try {
                resumeGame();
            } catch (PersistenceException ex) {
                throw new RuntimeException(ex);
            }
        });
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

    public void resumeGameButton() throws PersistenceException {
        Map<Integer, Integer> games = getUnfinishedGames();
        homeView.displayGames(games);
    }

    public void buyGenerator(String type) {
        int generatorId;
        if (generatorManager.generatorPurchaseAvailable(gameManager.getGameCurrency(), gameManager.getGameId(), type)) {
            generatorId = generatorManager.purchaseNewGenerator(type, gameManager.getGameId());
            //gameManager.buyGenerator();
        }
        //generatorManager.purchaseNewGenerator(type, gameManager.getGameId());
    }

    public String getEmail_id () {
        return userManager.getCurrentUser().getEmail();
    }

    public void addGame(int id, int currency_count, boolean finished, String mail_user) throws PersistenceException {
        //gameManager.addGame(id, currency_count, finished, mail_user);
    }
    public void registerUser(String username, String email, String password, String confirmPassword) throws BusinessException, ConnectionErrorException {
        userManager.registerUser(username, email, password, confirmPassword);
    }

    public void restartValuesUser() {
        userManager.restartValuesUser();
    }

    public void deleteUser() throws ConnectionErrorException, ConnectionErrorException {
        userManager.deleteUser();
    }

    public Map<Integer, Integer> getUnfinishedGames() throws PersistenceException {
        return gameManager.getUnfinishedGames(getEmail_id());
    }

    public void loginUser(String userLoginMail, String userLoginPass) throws UserException, UserException {
        userManager.loginUser(userLoginMail, userLoginPass);
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

        gameView.initialize(n_currencies, n_generators[0], n_generators[1], n_generators[2], boosts_lvl[0],boosts_lvl[1],boosts_lvl[2]);
    }
}