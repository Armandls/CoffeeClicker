package Presentation;

import Business.Exception.BusinessException;
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

import java.awt.*;
import java.io.IOException;
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

    void init() throws IOException {
        mainFrame = new MainFrame();
        views = new Hashtable<>();

        LoginController loginController = new LoginController(this);
        loginView = new LoginView(loginController);

        RegisterController registerController = new RegisterController(this);
        registerView = new RegisterView(registerController);

        StoresController storesController = new StoresController(this);
        storesView = new StoresView(storesController);

        GameController gameController = new GameController(this);
        gameView = new GameView(gameController, storesView, 0);

        HomeController homeController = new HomeController(this);
        homeView = new HomeView(homeController);
        homeView.addNewGameButtonListener(homeController);
        homeView.addResumeGameButtonListener(e -> {
            try {
                resumeGameButton();
            } catch (PersistenceException ex) {
                throw new RuntimeException(ex);
            }
        });
        mainFrame.addPanel(loginView, "login");
        mainFrame.addPanel(gameView, "game");
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

    public void resumeGameButton() throws PersistenceException {
        Map<Integer, Integer> games = getUnfinishedGames();
        homeView.displayGames(games);
    }

    public void buyGenerator(String type) {
        boolean validPurchase;
        try {
            validPurchase = gameManager.buyGenerator(type);
            if (validPurchase) {
                getGeneratorInfo();
            } else {
                //Mostra missatge de que no es te suficient diners per comprar;
                System.out.println("Not enough money you have " + gameManager.getGameCurrency());
            }
        } catch (BusinessException e) {
            //Printeja el missatge d'error on toqui.
        }
    }


    public String getEmail_id() {
        return userManager.getCurrentUser().getEmail();
    }

    public void addGame(int id, int currency_count, boolean finished, String mail_user) throws PersistenceException {
        //gameManager.addGame(id, currency_count, finished, mail_user);
    }

    public void registerUser(String username, String email, String password, String confirmPassword) throws BusinessException, ConnectionErrorException {
        userManager.registerUser(username, email, password, confirmPassword);
    }

    public void getGeneratorInfo() {
        try {
            //Pillar info dels generadors per passar.
            int auxGameId = gameManager.getGameId();
            String shopNames[] = generatorManager.getShopNames();
            int shopPrices[] = generatorManager.getShopPrices(auxGameId);
            int shopNumGens[] = generatorManager.getNumGeneratorsInShop(auxGameId);
            String shopImages[] = generatorManager.getShopImages();

            int currencyActualGame = gameManager.getGameCurrency();

            //for (int i = 0; i < 3; i++) {
            //System.out.println("\nGenerador " + (i + 1));
            //System.out.printf("Nom: %s\nPreu: %d\nNumero Generadors: %d\nPath Imatge:\n\t%s\n", shopNames[i], shopPrices[i], shopNumGens[i], shopImages[i]);
            //}

            //Passar la info a la view

        } catch (BusinessException e) {
            //Printejar el missatge d'error
        }
    }
    public void restartValuesUser () {
        userManager.restartValuesUser();
    }
    public void deleteUser () throws ConnectionErrorException, ConnectionErrorException {
        userManager.deleteUser();
    }
    public Map<Integer, Integer> getUnfinishedGames () throws PersistenceException {
        return gameManager.getUnfinishedGames(getEmail_id());
    }
    public void loginUser (String userLoginMail, String userLoginPass) throws UserException {
        userManager.loginUser(userLoginMail, userLoginPass);
    }
    public void registerEnterValid (String what){
        if (what.equals("email")) {
            registerView.enterValidEmail();
        } else {
            registerView.enterValidPassword();
        }
    }
    public void loginEnterValid (String what){
        if (what.equals("email")) {
            loginView.enterValidEmail();
        } else {
            loginView.enterValidPassword();
        }
    }
    public String[] getLoginInfo () {
        return loginView.getInfo();
    }
    public void clearForm (String what){
        switch (what) {
            case "login":
                loginView.clearForm();
                break;
            case "register":
                registerView.clearForm();
                break;
        }
    }

    public void adviceMessage (String error, String databaseError, String what){
        if (what.equals("login")) {
            loginView.adviceMessage(error, databaseError);
        } else {
            registerView.adviceMessage(error, databaseError);
        }
    }

    public void showProfile () {
        gameView.stop();
        gameView.showProfile();
    }

    public void showConfig () {
        gameView.stop();
        gameView.showConfig();
    }

    public void toggleStore () {
        gameView.toggleStore();
    }

    public void startRedPanelAnimation (Point location){
        gameView.increase();
        gameView.startRedPanelAnimation(location);
    }

    public void hideProfile () {
        gameView.start();
        gameView.hideProfile();
    }

    public void hideConfig () {
        gameView.start();
        gameView.hideConfig();
    }

    public String[] getRegisterInfo () {
        return registerView.getInfo();
    }

    public void resumeGame(int gameId) {
        int n_currencies = 0;
        int[] n_generators = {0,0,0}; //{n_basic, n_mid, n_high}
        int[] boosts_lvl = {0,0,0};   //{lvl_basic, lvl_mid, lvl_high}
        List<String> generator_types;
        int i = 0;

        // 1. retreiem dades de la partida que es mostraran a les views
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

        //2. Afegim a la classe game els generadors guardats a la partida que es vol restaurar
        try {
            String user = userManager.getCurrentUser().getEmail();
            gameManager.initGame(gameId, n_currencies, user);
        } catch (BusinessException e) {
            throw new RuntimeException(e); // no generators exception/persistance exception
        }
        gameView.initialize(n_currencies, n_generators[0], n_generators[1], n_generators[2], boosts_lvl[0], boosts_lvl[1], boosts_lvl[2]);
    }
}