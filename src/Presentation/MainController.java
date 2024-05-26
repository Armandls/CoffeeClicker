package Presentation;

import Business.Exception.BusinessException;
import Business.Exception.GeneratorException.GeneratorAddedException;
import Business.Exception.GeneratorException.NoGeneratorException;
import Business.Exception.GeneratorException.NotEnoughCurrencyException;
import Business.Exception.UserException.UserException;
import Business.GameManager;
import Business.GeneratorManager;
import Business.UserManager;
import Persistance.Exception.ConnectionErrorException;
import Persistance.Exception.NotFoundException;
import Persistance.Exception.PersistenceException;
import Presentation.Controllers.*;
import Presentation.Interfaces.*;
import Presentation.Views.*;

import java.awt.*;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Class to manage the interactions between the user interface (UI, the View) and the Manager classes
 */
public class MainController implements ThreadController, GameControllerI, HomeControllerI, LoginControllerI, RegisterControllerI, StatisticsControllerI, StoresControllerI {

    private GameManager gameManager;
    private final GeneratorManager generatorManager;
    private final UserManager userManager;
    private MainFrame mainFrame;
    private MyView currentView;
    private Hashtable<String, MyView> views;
    private LoginView loginView;
    private RegisterView registerView;
    private StoresView storesView;
    private HomeView homeView;
    private GameView gameView;
    private StatisticsView statisticsView;
    private GeneratorsView generatorsView;
    private ImprovementsView improvementsView;


    public MainController(GeneratorManager generatorManager, UserManager userManager) throws IOException {
        this.generatorManager = generatorManager;
        this.userManager = userManager;
        init();
    }

    public void swapPanel(String panelName) {
        mainFrame.showPanel(panelName);
        currentView.clear();
        currentView = views.get(panelName);
    }

    public void initializeGame(int currency, int basicGenerator, int midGenerator, int highGenerator, int lvlBasicImp, int lvlMidImp, int lvlHighImp) {
        gameView.initialize(currency, basicGenerator, midGenerator, highGenerator, lvlBasicImp, lvlMidImp, lvlHighImp);
    }

    /**
     * Initializes the MainController by creating the necessary views and controllers.
     * @throws IOException if an I/O error occurs while initializing the views.
     */
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

        StatisticsController statisticsController = new StatisticsController(this);
        statisticsView = new StatisticsView(statisticsController);

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

    /**
     * Resumes the game by loading the unfinished games and displaying them.
     * @throws PersistenceException if an error occurs during persistence operations.
     */
    public void resumeGameButton() throws PersistenceException {
        this.gameManager = new GameManager(generatorManager);
        this.gameManager.setThreadController(this);
        Map<Integer, Integer> games = getUnfinishedGames();
        homeView.displayGames(games);
    }


    /**
     * Handles the purchase of a generator.
     * @param type the type of generator to purchase.
     */
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
                gameView.updateTable(generatorManager.getAllNumberOfGenerators(gameManager.getGameId()), generatorManager.getAllProductionPerSec(gameManager.getGameId()), generatorManager.getAllProductionPercentage(gameManager.getGameId()), generatorManager.getShopPrices(gameManager.getGameId()), generatorManager.getImprovementLevels(gameManager.getGameId()));
                updateStoresGeneratorsView();
            } else {
                //Mostra missatge de que no es te suficient diners per comprar;
                storesView.noEnoughMoney(gameManager.getGameCurrency());
            }
        } catch (BusinessException e) {
            //Printeja el missatge d'error on toqui.
        }
    }


    /**
     * Retrieves the email ID of the current user.
     * @return the email ID of the current user.
     */
    public String getEmail_id() {
        return userManager.getCurrentUser().getEmail();
    }



    /**
     * Creates a new game for the current user.
     */
    public void createNewGame() {
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

    /**
     * Registers a new user with the provided information.
     * @param username the username of the new user.
     * @param email the email address of the new user.
     * @param password the password of the new user.
     * @param confirmPassword the confirmed password of the new user.
     * @throws BusinessException if an error occurs during user registration.
     * @throws ConnectionErrorException if there is an error with the connection.
     */
    public void registerUser(String username, String email, String password, String confirmPassword) throws BusinessException, ConnectionErrorException {
        userManager.registerUser(username, email, password, confirmPassword);
    }


    /**
     * Restarts the values of the current user and sets the running game to false.
     */
    public void restartValuesUser () {
        userManager.restartValuesUser();
        gameManager.setRunningGame(false);
    }


    /**
     * Deletes the current user.
     * @throws ConnectionErrorException if there is an error with the connection.
     */
    @Override
    public void deleteUser () throws ConnectionErrorException {
        userManager.deleteUser();
        gameManager.setRunningGame(false);
    }

    /**
     * Retrieves the unfinished games for the current user.
     * @return a map containing the unfinished games with their respective IDs and currencies.
     * @throws PersistenceException if an error occurs during persistence operations.
     */
    public Map<Integer, Integer> getUnfinishedGames () throws PersistenceException {
        return gameManager.getUnfinishedGames(getEmail_id());
    }

    /**
     * Logs in the user with the provided email and password.
     * @param userLoginMail the email of the user to log in.
     * @param userLoginPass the password of the user to log in.
     * @throws UserException if there is an error with the user login.
     */
    public void loginUser (String userLoginMail, String userLoginPass) throws UserException {
        userManager.loginUser(userLoginMail, userLoginPass);
    }

    /**
     * Displays a message to prompt the user to enter valid information during registration.
     * @param what the type of information that needs to be valid.
     */
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

    /**
     * Displays a message to prompt the user to enter valid information during login.
     * @param what the type of information that needs to be valid.
     */
    public void loginEnterValid (String what){
        if (what.equals("email")) {
            loginView.enterValidEmail();
        } else {
            loginView.enterValidPassword();
        }
    }
    /**
     * Retrieves the login information entered by the user.
     * @return an array containing the login information.
     */
    public String[] getLoginInfo () {
        return loginView.getInfo();
    }


    /**
     * Clears the form based on the specified type.
     * @param what the type of form to clear.
     */
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

    /**
     * Displays an advice message to the user during registration or login.
     * @param error the error message to display.
     * @param databaseError the database error message to display.
     * @param what the type of action (registration or login).
     */
    public void adviceMessage (String error, String databaseError, String what){
        if (what.equals("login")) {
            loginView.adviceMessage(error, databaseError);
        } else {
            registerView.adviceMessage(error, databaseError);
        }
    }

    /**
     * Displays the user profile.
     */
    public void showProfile () {
        gameView.stop();
        gameView.showProfile();
    }


    /**
     * Displays the configuration settings.
     */
    public void showConfig () {
        gameView.stop();
        gameView.showConfig();
    }


    /**
     * Toggles the store view.
     */
    public void toggleStore () {
        updateStoresGeneratorsView();
        gameView.toggleStore();
    }

    /**
     * Starts the red panel animation at the specified location.
     * @param location the location where the animation should start.
     */
    public void startRedPanelAnimation (Point location){
        gameManager.increaseCurrency();
        gameView.updateCurrency(gameManager.getGameCurrency());
        gameView.startRedPanelAnimation(location);
    }

    /**
     * Hides the user profile.
     */
    public void hideProfile () {
        gameView.start();
        gameView.hideProfile();
    }

    /**
     * Hides the configuration settings.
     */
    public void hideConfig () {
        gameView.start();
        gameView.hideConfig();
    }


    /**
     * Retrieves the registration information entered by the user.
     * @return an array containing the registration information.
     */
    public String[] getRegisterInfo () {
        return registerView.getInfo();
    }

    /**
     * Resumes a game with the specified game ID.
     * @param gameId the ID of the game to resume.
     * @throws BusinessException if an error occurs during game resumption.
     */
    public void resumeGame(int gameId) throws BusinessException {
        int n_currencies;
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
        gameView.updateTable(generatorManager.getAllNumberOfGenerators(gameManager.getGameId()), generatorManager.getAllProductionPerSec(gameManager.getGameId()), generatorManager.getAllProductionPercentage(gameManager.getGameId()), generatorManager.getShopPrices(gameManager.getGameId()), generatorManager.getImprovementLevels(gameManager.getGameId()));
        gameManager.setRunningGame(true);
        gameManager.start();
    }

    /**
     * Updates the view of the store and generators.
     */
    public void updateStoresGeneratorsView() {
        /*try {
            //Pillar info dels generadors per passar.
            int auxGameId = gameManager.getGameId();
            //storesView.updateGeneratorsView(generatorManager.getShopPrices(auxGameId), generatorManager.getNumGeneratorsInShop(auxGameId));
        } catch (BusinessException e) {
            //Printejar el missatge d'error
        }*/
    }

    /**
     * Updates the improvement with the specified name.
     * @param improvement the name of the improvement to update.
     */
    public void updateImprovement (String improvement) {
        try {
            gameManager.updateImprovement(improvement);
            storesView.updateImprovementsView(generatorManager.getImprovementLevels(gameManager.getGameId()));
        } catch (GeneratorAddedException | NotEnoughCurrencyException e) {
            if (e instanceof GeneratorAddedException)
                storesView.noGenerators(improvement);
            else
                storesView.noEnoughMoney(gameManager.getGameCurrency());
        }
    }

    /**
     * Checks if the password contains at least one lowercase character.
     * @param password the password to check.
     * @return true if the password contains at least one lowercase character, otherwise false.
     */
    public boolean checkLowerCaseCaracter(String password) {
        return userManager.checkLowerCaseCaracter(password);
    }

    /**
     * Checks if the password contains at least one uppercase character.
     * @param password the password to check.
     * @return true if the password contains at least one uppercase character, otherwise false.
     */
    public boolean checkUpperCaseCaracter(String password) {
        return userManager.checkUpperCaseCaracter(password);
    }

    /**
     * Checks if the password contains at least one number.
     * @param password the password to check.
     * @return true if the password contains at least one number, otherwise false.
     */
    public boolean checkMinOneNumber(String password) {
        return userManager.checkMinOneNumber(password);
    }

    /**
     * Checks if the email is valid.
     * @param email the email to check.
     * @return true if the email is valid, otherwise false.
     */
    public boolean checkValidEmail(String email) {
        return userManager.checkValidEmail(email);
    }

    /**
     * Checks if the username is valid.
     * @param username the username to check.
     * @return true if the username is valid, otherwise false.
     * @throws ConnectionErrorException if there is an error with the connection.
     */
    public boolean checkValidUsername(String username) throws ConnectionErrorException {
        return userManager.checkValidUsername(username);
    }

    /**
     * Swaps the panel in the store view.
     * @param panelName the name of the panel to swap.
     */
    @Override
    public void swapStore(String panelName) {
        ((StoresView)views.get("stores")).swapPanel(panelName);
    }

    /**
     * Updates the currency in the store view.
     * @param amount the amount of currency to update.
     */
    @Override
    public void updateStoreCurrency(float amount) {
        gameView.updateCurrency(gameManager.getGameCurrency());
    }

    /**
     * Logs out the user from the application.
     */
    @Override
    public void logout() {
        exit();
        gameManager.setRunningGame(false);
        swapPanel("login");
    }

    /**
     * Exits the application.
     */
    @Override
    public void exit() {
        int[] values = {0, 0, 0};
        float[] values2 = {0, 0, 0};
        ((GameView)this.views.get("game")).updateTable(values, values2, values2, values, values);
    }

    /**
     * Saves the current game state.
     * @param finished indicates whether the game is finished or not.
     * @throws PersistenceException if an error occurs during persistence operations.
     */
    public void saveGame(boolean finished) throws PersistenceException {
        gameManager.saveGame(finished);
        gameManager.setRunningGame(false);
    }

    /**
     * Fetches the finished games for the current user and updates the statistics view.
     */
    public void fetchGames() {
        try {
            Map<Integer, Integer> games = gameManager.getFinishedGames(userManager.getCurrentUser().getEmail());
            for (Map.Entry<Integer, Integer> entry : games.entrySet()) {
                Integer gameId = entry.getKey();
                Integer currencies = entry.getValue();
                statisticsView.addGame(gameId.toString(), String.valueOf(currencies));
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }


    /**
     * Displays the statistics of the game with the specified ID.
     * @param gameId the ID of the game to display statistics for.
     */
    public void displayGameStats(String gameId) {
        try {
            List<Integer> l = gameManager.getStatsFromGame(gameId);
            String[][] data = new String[l.size()][2];
            for(int i = 0; i < l.size(); i++) {
                data[i][0] = String.valueOf(i);
                data[i][1] = String.valueOf(l.get(i));
            }
            statisticsView.updateGraphic(data);
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Restarts the current game.
     */
    public void restartGame() {
        gameManager.restartGame();
    }
}