package Business;

import Business.Entities.Game;
import Business.Entities.Generator.Generator;
import Business.Exception.BusinessException;
import Business.Exception.GeneratorException.GeneratorAddedException;
import Business.Exception.GeneratorException.NoGeneratorException;
import Business.Exception.GeneratorException.NotEnoughCurrencyException;
import Persistance.DAO.GameDAO;
import Persistance.Exception.ConnectionErrorException;
import Persistance.Exception.NotFoundException;
import Persistance.Exception.PersistenceException;
import Persistance.SQL.SQLGameDAO;
import Presentation.Controllers.ThreadController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to manage the different entities regarding the Game.
 * @Currency, @ImprovementStore, @Game
 */
public class GameManager extends Thread{
    private final GeneratorManager generatorManager;
    private ThreadController threadController;
    private boolean runningGame;
    private final GameDAO gameDAO;
    private Game game;
    private int threadCount;
    private int min;

    /**
     * Constructor for GameManager.
     * @param gameDAO The GameDAO instance to use for database operations.
     * @param generatorManager The GeneratorManager instance to manage generators.
     */
    public GameManager(GameDAO gameDAO, GeneratorManager generatorManager) {
        this.gameDAO = gameDAO;
        this.generatorManager = generatorManager;
        threadCount = 0;
        min = 0;
    }

    /**
     * Alternative constructor for GameManager.
     * Uses default SQLGameDAO for database operations.
     * @param generatorManager The GeneratorManager instance to manage generators.
     */
    public GameManager(GeneratorManager generatorManager) {
        this.gameDAO = new SQLGameDAO();
        this.generatorManager = generatorManager;
        threadCount = 0;
        min = 0;
    }


    /**
     * Sets the ThreadController for this GameManager.
     * @param tc The ThreadController instance.
     */
    public void setThreadController(ThreadController tc) {
        this.threadController = tc;
    }

    /**
     * Sets the running state of the game.
     * @param value The running state.
     */
    public void setRunningGame(boolean value) {
        this.runningGame = value;
    }

    /**
     * Overrides the run method of Thread.
     * Manages the game loop and updates generators' production.
     */
    @Override
    public void run() {
        while (runningGame){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            game.generatorsProduction();
            for(Generator aux : game.getGameGenerators()) {
                try {
                    generatorManager.updateGenerator(aux);
                } catch (ConnectionErrorException e) {
                }
            }
            try {
                gameDAO.updateGame(this.game);
            } catch (PersistenceException e) {
            }
            threadController.updateStoreCurrency(getGameCurrency());

            // Update game statistics every minute.
            threadCount++;
            if (threadCount >= 60) {
                try {
                    gameDAO.addStatistic(game.getIdGame(), min, (int)game.getCurrencyCount());
                } catch (ConnectionErrorException e) {
                    // Handle exception
                }
                threadCount = 0;
                min++;
            }
        }
    }

    /**
     * Initializes the game with the provided parameters.
     * @param gameId The ID of the game.
     * @param n_currencies The initial currency count.
     * @param user The user associated with the game.
     * @throws BusinessException If no generators are found for the game.
     */
    public void initGame(int gameId, int n_currencies, String user) throws BusinessException {
        List<Generator> generators;
        this.game = new Game(gameId, n_currencies, false, user);
        try {
            generators = generatorManager.getGenerators(gameId);
            for(Generator g: generators) {
                game.initGenerator(g);
            }
        } catch (BusinessException e) {
            throw new NoGeneratorException("No generators were found for game with id ->" + gameId);
        }
    }

    // Additional methods and functionalities...

    /**
     * Retrieves the ID of the current game.
     * @return The ID of the game.
     */
    public int getGameId() {
        return game.getIdGame();
    }

    /**
     * Retrieves the current currency count of the game in real-time, without querying the database.
     * @return The current currency count.
     */
    public int getGameCurrency() {
        return (int) game.getCurrencyCount();
    }

    /**
     * Buys a generator of the specified type for the current game.
     * @param type The type of generator to buy.
     * @return True if the purchase is successful, false otherwise.
     * @throws BusinessException If an error occurs during the purchase process.
     */
    public boolean buyGenerator(String type) throws BusinessException {
        int[] generatorId;
        if (generatorManager.generatorPurchaseAvailable(game.getCurrencyCount(), game.getIdGame(), type)) {
            generatorId = generatorManager.purchaseNewGenerator(type, game.getIdGame());
            game.addGeneratorToGame(type, generatorId[0], generatorId[1]);
            try {
                gameDAO.updateGame(game);
            } catch (PersistenceException e) {
                throw new BusinessException(e.getMessage());
            }
            return true;
        }
        return false;
    }

    /**
     * Creates a new game for the specified user.
     * @param mail The email of the user.
     * @throws BusinessException If an error occurs during game creation.
     */
    public void createNewGame(String mail) throws BusinessException{
        this.game = new Game(0, 0, false, mail);
        try {
            int generatedId = gameDAO.addGame(this.game);
            this.game.setId_game(generatedId);
        } catch (ConnectionErrorException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Adds a game with the specified details to the database.
     * @param id The ID of the game.
     * @param currency_count The currency count of the game.
     * @param finished The finished state of the game.
     * @param mail_user The email of the user associated with the game.
     * @throws PersistenceException If an error occurs during database interaction.
     */
    public void addGame(int id, int currency_count, boolean finished, String mail_user) throws PersistenceException {
        try {
            int idGame = gameDAO.addGame(new Game(id, currency_count, finished, mail_user));
            game = new Game (idGame, currency_count, finished, mail_user);
        }catch (PersistenceException exception) {
            throw new PersistenceException("ERROR: Couldn't add game to the database.");
        }
    }

    /**
     * Retrieves a map of IDs and currency counts for all unfinished games of the specified user.
     * @param mail_user The email of the user.
     * @return A map containing game IDs as keys and currency counts as values.
     * @throws PersistenceException If an error occurs during database interaction.
     */
    public Map<Integer, Integer> getUnfinishedGames(String mail_user) throws PersistenceException {
        List<Game> games;
        Map<Integer, Integer> creditsAndIds = new HashMap<>();
        try {
            games = gameDAO.getUnfinishedGamesFromUser(mail_user);
        } catch (PersistenceException exception) {
            throw new PersistenceException("ERROR: Couldn't get the user's unfinished games from the database.");
        }
        for (Game game : games) {
            creditsAndIds.put(game.getIdGame(), Math.round(game.getCurrencyCount()));
        }
        return creditsAndIds;
    }

    /**
     * Retrieves a map of IDs and currency counts for all finished games of the specified user.
     * @param mail_user The email of the user.
     * @return A map containing game IDs as keys and currency counts as values.
     * @throws PersistenceException If an error occurs during database interaction.
     */
    public Map<Integer, Integer> getFinishedGames(String mail_user) throws PersistenceException {
        List<Game> games;
        Map<Integer, Integer> creditsAndIds = new HashMap<>();
        try {
            games = gameDAO.getFinishedGamesFromUser(mail_user);
        } catch (PersistenceException exception) {
            throw new PersistenceException("ERROR: Couldn't get the user's finished games from the database.");
        }
        for (Game game : games) {
            creditsAndIds.put(game.getIdGame(), Math.round(game.getCurrencyCount()));
        }
        return creditsAndIds;
    }

    /**
     * Retrieves the currency count for the game with the specified ID.
     * @param gameId The ID of the game.
     * @return The currency count of the game.
     * @throws NotFoundException If the game with the specified ID is not found.
     */
    public int getGameCurrencies(int gameId) throws NotFoundException {
        Game game2;
        try {
            game2 = gameDAO.getGame(gameId);
        } catch (PersistenceException e) {
            throw new NotFoundException("ERROR: Couldn't get the requested game.");
        }
        return Math.round(game2.getCurrencyCount());
    }

    /**
     * Increases the currency count of the current game.
     */
    public void increaseCurrency() {
        game.increaseCurrency();
    }

    /**
     * Updates the currency count of the current game to the specified value.
     * @param nCurrencies The new currency count.
     * @throws BusinessException If an error occurs during the update process.
     */
    public void updateCurrency(int nCurrencies) throws BusinessException {
        game.updateCurrency(nCurrencies);
        try {
            gameDAO.updateGame(this.game);
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Retrieves the currency count of the current game from the database.
     * @return The currency count of the game.
     * @throws BusinessException If an error occurs during the retrieval process.
     */

    public void updateImprovement(String improvement) throws GeneratorAddedException, NotEnoughCurrencyException {
        if (improvement.equals("Pills")) {
            if (game.getGameGenerators().size() > 0 && game.getGameGenerators().get(0) != null && game.getGameGenerators().get(0).getNGens() > 0) {
                if (game.getCurrencyCount() >= 100) {
                    game.substractCurrency(100);
                    game.getGameGenerators().get(0).incrementImprovementLevel();
                    try {
                        generatorManager.updateGenerator(game.getGameGenerators().get(0));
                    } catch (PersistenceException e) {
                        throw new GeneratorAddedException("Error updating generator with id <" + game.getGameGenerators().get(0).getIdGenerator() + ">. " + e.getMessage());
                    }
                } else {
                    throw new NotEnoughCurrencyException("Not enough currency to update generator with id <" + game.getGameGenerators().get(0).getIdGenerator() + ">");
                }
            } else {
                throw new GeneratorAddedException("No generators of type 'Pills' found.");
            }
        } else if (improvement.equals("Glasses")) {
            if (game.getGameGenerators().size() > 1 && game.getGameGenerators().get(1) != null && game.getGameGenerators().get(1).getNGens() > 0) {
                if (game.getCurrencyCount() >= 250) {
                    game.substractCurrency(250);
                    game.getGameGenerators().get(1).incrementImprovementLevel();
                    try {
                        generatorManager.updateGenerator(game.getGameGenerators().get(1));
                    } catch (PersistenceException e) {
                        throw new GeneratorAddedException("Error updating generator with id <" + game.getGameGenerators().get(1).getIdGenerator() + ">. " + e.getMessage());
                    }
                } else {
                    throw new NotEnoughCurrencyException("Not enough currency to update generator with id <" + game.getGameGenerators().get(1).getIdGenerator() + ">");
                }
            } else {
                throw new GeneratorAddedException("No generators of type 'Glasses' found.");
            }
        } else if (improvement.equals("Carlos")) {
            if (game.getGameGenerators().size() > 2 && game.getGameGenerators().get(2) != null && game.getGameGenerators().get(2).getNGens() > 0) {
                if (game.getCurrencyCount() >= 500) {
                    game.substractCurrency(500);
                    game.getGameGenerators().get(2).incrementImprovementLevel();
                    try {
                        generatorManager.updateGenerator(game.getGameGenerators().get(2));
                    } catch (PersistenceException e) {
                        throw new GeneratorAddedException("Error updating generator with id <" + game.getGameGenerators().get(2).getIdGenerator() + ">. " + e.getMessage());
                    }
                } else {
                    throw new NotEnoughCurrencyException("Not enough currency to update generator with id <" + game.getGameGenerators().get(2).getIdGenerator() + ">");
                }
            } else {
                throw new GeneratorAddedException("No generators of type 'Carlos' found.");
            }
        } else {
            throw new GeneratorAddedException("Unknown improvement type: " + improvement);
        }
    }

    /**
     * Retrieves the currency count of the current game from the database.
     * @return The currency count of the game.
     * @throws BusinessException If an error occurs during the retrieval process.
     */

    public void saveGame(boolean finished) throws PersistenceException {
        game.setFinished(finished);
        gameDAO.updateGame(game);
    }

    /**
     * Retrieves the currency count of the current game from the database.
     * @return The currency count of the game.
     * @throws BusinessException If an error occurs during the retrieval process.
     */

    public List<Integer> getStatsFromGame(String id) throws BusinessException {
        try {
            return gameDAO.getGameStatistics(id);
        } catch (PersistenceException e) {
            throw new BusinessException("Could not fetch game with id " + id);
        }
    }

    /**
     * Retrieves the currency count of the current game from the database.
     * @return The currency count of the game.
     * @throws BusinessException If an error occurs during the retrieval process.
     */
    public void restartGame() {
        // remove from list
        game.getGameGenerators().clear();

    }
}
