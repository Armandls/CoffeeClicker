package Presentation;

import Business.Exception.BusinessException;
import Business.Exception.GeneratorException.GeneratorAddedException;
import Business.Exception.GeneratorException.NoGeneratorException;
import Business.Exception.UserException.UserException;
import Business.GameManager;
import Business.GeneratorManager;
import Business.UserManager;
import Persistance.Exception.ConnectionErrorException;
import Persistance.Exception.NotFoundException;
import Persistance.Exception.PersistenceException;
import Presentation.Controllers.*;
import Presentation.Views.*;

import java.awt.*;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Class to manage the interactions between the user interface (UI, the View) and the Manager classes
 */
public class MainController implements FrameController, ThreadController {

    private GameManager gameManager;
    private GeneratorManager generatorManager;
    private UserManager userManager;
    private MainFrame mainFrame;
    private MyView currentView;
    private Hashtable<String, MyView> views;
    private LoginView loginView;
    private RegisterView registerView;
    private StoresView storesView;
    private HomeView homeView;
    private GameView gameView;

    private GeneratorsView generatorsView;
    private ImprovementsView improvementsView;


    public MainController(GeneratorManager generatorManager, UserManager userManager) throws IOException, BusinessException {
        //this.gameManager = gameManager;
        //this.gameManager.setThreadController(this);
        this.generatorManager = generatorManager;
        this.userManager = userManager;
        init();
    }

    @Override
    public void swapPanel(String panelName) {
        mainFrame.showPanel(panelName);
        currentView = views.get(panelName);
    }

    @Override
    public void initializeGame(int currency, int basicGenerator, int midGenerator, int highGenerator, int lvlBasicImp, int lvlMidImp, int lvlHighImp) throws BusinessException {
        gameView.initialize(currency, basicGenerator, midGenerator, highGenerator, lvlBasicImp, lvlMidImp, lvlHighImp);
    }

    void init() throws IOException, BusinessException {
        mainFrame = new MainFrame();
        views = new Hashtable<>();

        LoginController loginController = new LoginController(this);
        loginView = new LoginView(loginController);

        RegisterController registerController = new RegisterController(this);
        registerView = new RegisterView(registerController);

        StoresController storesController = new StoresController(this);
        storesView = new StoresView(storesController, this);

        GameController gameController = new GameController(this);
        gameView = new GameView(gameController, storesView, 0);

        HomeController homeController = new HomeController(this);
        homeView = new HomeView(homeController);

        StatisticsController statisticsController = new StatisticsController(this);
        StatisticsView statisticsView = new StatisticsView(statisticsController);

        mainFrame.addPanel(loginView, "login");
        mainFrame.addPanel(gameView, "game");
        mainFrame.addPanel(registerView, "register");
        mainFrame.addPanel(homeView, "home");
        mainFrame.addPanel(statisticsView, "statistics");
        mainFrame.setVisible(true);

        views.put("login", loginView);
        views.put("register", registerView);
        views.put("game", gameView);
        views.put("home", homeView);
        views.put("stores", storesView);
        views.put("statistics", statisticsView);

        currentView = loginView;

        initializeGame(0, 0, 0, 0, 0, 0, 0);
    }

    public void resumeGameButton() throws PersistenceException {
        this.gameManager = new GameManager(generatorManager);
        this.gameManager.setThreadController(this);
        Map<Integer, Integer> games = getUnfinishedGames();
        homeView.displayGames(games);
    }

    public void buyGenerator(String type) {
        boolean validPurchase;
        switch (type) {
            case "Redbull":
                type = "Basic";
                break;
            case "Notes":
                type = "Mid";
                break;
            case "CEUS":
                type = "High";
                break;
        }
        try {
            validPurchase = gameManager.buyGenerator(type);
            if (validPurchase) {
                gameView.updateCurrency(gameManager.getGameCurrency());
                gameView.updateTable(generatorManager.getAllNumberOfGenerators(gameManager.getGameId()), generatorManager.getAllProductionPerSec(gameManager.getGameId()), generatorManager.getAllProductionPercentage(gameManager.getGameId(), gameManager.getGameCurrency()));
                updateStoresGeneratorsView();
            } else {
                //Mostra missatge de que no es te suficient diners per comprar;
                System.out.println("Not enough money, you have " + gameManager.getGameCurrency());
            }
        } catch (BusinessException e) {
            //Printeja el missatge d'error on toqui.
        }
    }

    public String getEmail_id() {
        return userManager.getCurrentUser().getEmail();
    }

    public void addGame(int id, int currency_count, boolean finished, String mail_user) throws PersistenceException {
        gameManager.addGame(id, currency_count, finished, mail_user);
    }
    public void createNewGame() throws PersistenceException {
        try {
            String email = getEmail_id();
            this.gameManager = new GameManager(generatorManager);
            this.gameManager.setThreadController(this);
            gameManager.createNewGame(email);
            gameManager.setRunningGame(true);
            gameManager.interrupt();
            gameManager.start();
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }
    }

    public void registerUser(String username, String email, String password, String confirmPassword) throws BusinessException, ConnectionErrorException {
        userManager.registerUser(username, email, password, confirmPassword);
    }
    public void restartValuesUser () {
        userManager.restartValuesUser();
        gameManager.setRunningGame(false);
        //gameManager.stopThread();
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

        if (what.equals("username")) {
            registerView.enterValidUsername();
        } else {
            if (what.equals("email")) {
                registerView.enterValidEmail();
            } else {
                if (what.equals("lengthPassword")) {
                    registerView.enterValidPassword8C();
                } else {
                    if (what.equals("lowerCasePassword")) {
                        registerView.enterValidPasswordLowerCase();
                    } else {
                        if (what.equals("upperCasePassword")) {
                            registerView.enterValidPasswordUpperCase();
                        } else {
                            if (what.equals("minOneNumber")) {
                                registerView.enterValidPasswordOneNumber();
                            }

                        }
                    }
                }
            }
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
        updateStoresGeneratorsView();
        gameView.toggleStore();
    }

    public void startRedPanelAnimation (Point location){
        try {
            gameManager.increaseCurrency();
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }
        gameView.updateCurrency(gameManager.getGameCurrency());
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

    public void resumeGame(int gameId) throws BusinessException {
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
        } catch (NoGeneratorException e) {
            // no generators exception/persistance exception
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }

        //2. Afegim a la classe game els generadors guardats a la partida que es vol restaurar
        String user = userManager.getCurrentUser().getEmail();
        try {
            gameManager.initGame(gameId, n_currencies, user);
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }

        gameView.initialize(n_currencies, n_generators[0], n_generators[1], n_generators[2], boosts_lvl[0], boosts_lvl[1], boosts_lvl[2]);
        gameView.updateTable(generatorManager.getAllNumberOfGenerators(gameManager.getGameId()), generatorManager.getAllProductionPerSec(gameManager.getGameId()), generatorManager.getAllProductionPercentage(gameManager.getGameId(), gameManager.getGameCurrency()));
        gameManager.setRunningGame(true);
        gameManager.start();
    }

    public void updateStoresGeneratorsView() {
        /*try {
            //Pillar info dels generadors per passar.
            int auxGameId = gameManager.getGameId();
            //storesView.updateGeneratorsView(generatorManager.getShopPrices(auxGameId), generatorManager.getNumGeneratorsInShop(auxGameId));
        } catch (BusinessException e) {
            //Printejar el missatge d'error
        }*/
    }

    public void updateImprovement (String improvement) {
        try {
            gameManager.updateImprovement(improvement);
            storesView.updateImprovementsView(generatorManager.getLevelOfGenerator(gameManager.getGameId(), "Basic"), generatorManager.getLevelOfGenerator(gameManager.getGameId(), "Mid"),generatorManager.getLevelOfGenerator(gameManager.getGameId(), "High"));
        } catch (GeneratorAddedException e) {
            storesView.noGenerators(improvement);
        }
    }

    public boolean checkLowerCaseCaracter(String password) {
        return userManager.checkLowerCaseCaracter(password);
    }

    public boolean checkUpperCaseCaracter(String password) {
        return userManager.checkUpperCaseCaracter(password);
    }

    public boolean checkMinOneNumber(String password) {
        return userManager.checkMinOneNumber(password);
    }

    public boolean checkValidEmail(String email) {
        return userManager.checkValidEmail(email);
    }

    public boolean checkValidUsername(String username) throws ConnectionErrorException {
        return userManager.checkValidUsername(username);
    }

    @Override
    public void removeHoverPanel(String name) {
        //gameView.removeHoverPanel(name);
    }

    @Override
    public void addHoverPanel(JHoverPanel panel) {
        //((GameView)views.get("game")).addHoverPanel(panel);
    }

    @Override
    public void swapStore(String panelName) {
        ((StoresView)views.get("stores")).swapPanel(panelName);
    }

    @Override
    public void updateStoreCurrency(float amount) {
        gameView.updateCurrency(gameManager.getGameCurrency());
    }

    public void saveGame(boolean finished) throws PersistenceException {
        gameManager.saveGame(finished);
    }
}