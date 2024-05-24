package Business;

import Business.Entities.Game;
import Business.Entities.Generator.Generator;
import Business.Exception.BusinessException;
import Business.Exception.GeneratorException.GeneratorAddedException;
import Business.Exception.GeneratorException.NoGeneratorException;
import Persistance.DAO.GameDAO;
import Persistance.Exception.ConnectionErrorException;
import Persistance.Exception.NotFoundException;
import Persistance.Exception.PersistenceException;
import Persistance.SQL.SQLGameDAO;
import Presentation.Controllers.ThreadController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*Class to manage the different entities regarding the Game
 *  @Currency, @ImprovementStore, @Game
 */
public class GameManager extends Thread{
    private final GeneratorManager generatorManager;
    private ThreadController threadController;
    private boolean runningGame;
    private final GameDAO gameDAO;
    private Game game;
    private int threadCount;
    private int min;
    public GameManager (GameDAO gameDAO, GeneratorManager generatorManager) {
        this.gameDAO = gameDAO;
        this.generatorManager = generatorManager;
        threadCount = 0;
        min = 0;
        //this.game = new Game();
    }
    public GameManager(GeneratorManager generatorManager) {
        this.gameDAO = new SQLGameDAO();
        this.generatorManager = generatorManager;
        threadCount = 0;
        min = 0;
        //this.game = new Game();
    }

    public void setThreadController(ThreadController tc) {
        this.threadController = tc;
    }

    public void setRunningGame(boolean value) {
        this.runningGame = value;
    }

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

            //Mirem de fer l'actualitzacio de les estadistiques de cada minut.
            threadCount++;
            if (threadCount >= 60) {
                try {
                    gameDAO.addStatistic(game.getIdGame(), min, (int)game.getCurrencyCount());
                } catch (ConnectionErrorException e) {
                    // Gestionar excepció
                }
                threadCount = 0;
                min++;
            }
        }
    }
    public void initGame(int gameId, int n_currencies, String user) throws BusinessException{
        List<Generator> generators;
        this.game = new Game(gameId, n_currencies, false, user);
        try {
            generators = generatorManager.getGenerators(gameId);
            for(Generator g: generators) {
                game.initGenerator(g);
            }
        } catch (BusinessException e) {
            throw new NoGeneratorException("No generators were found for game with id ->" + "gameId");
        }
    }
    public int getGameId() {
        return game.getIdGame();
    }

    /**
     * Get del valor de currency que es te en temps real de la partida, no consulta la base de dades.
     * @return
     */
    public int getGameCurrency() {return (int) game.getCurrencyCount();}
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
    public void createNewGame(String mail) throws BusinessException{
        this.game = new Game(0, 0, false, mail);
        try {
            int generatedId = gameDAO.addGame(this.game);
            this.game.setId_game(generatedId);
        } catch (ConnectionErrorException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public void addGame(int id, int currency_count, boolean finished, String mail_user) throws PersistenceException {
        try {
            int idGame = gameDAO.addGame(new Game(id, currency_count, finished, mail_user));
            game = new Game (idGame, currency_count, finished, mail_user);
        }catch (PersistenceException exception) {
            throw new PersistenceException("ERROR: Couldn't add game to the database.");
        }

    }
    public Map<Integer, Integer> getUnfinishedGames (String mail_user) throws PersistenceException {
        List<Game> games;
        Map<Integer, Integer> creditsAndIds = new HashMap<>();
        try {
            games = gameDAO.getUnfinishedGamesFromUser(mail_user);
        }catch (PersistenceException exception) {
            throw new PersistenceException("ERROR: Couldn't get the users' unfinished games from the database.");
        }
        for (Game game : games) {
            creditsAndIds.put(game.getIdGame(), Math.round(game.getCurrencyCount()));
        }
        return creditsAndIds;
    }


    public Map<Integer, Integer> getFinishedGames (String mail_user) throws PersistenceException {
        List<Game> games;
        Map<Integer, Integer> creditsAndIds = new HashMap<>();
        try {
            games = gameDAO.getFinishedGamesFromUser(mail_user);
        }catch (PersistenceException exception) {
            throw new PersistenceException("ERROR: Couldn't get the users' unfinished games from the database.");
        }
        for (Game game : games) {
            creditsAndIds.put(game.getIdGame(), Math.round(game.getCurrencyCount()));
        }
        return creditsAndIds;
    }

    public int getGameCurrencies(int gameId) throws NotFoundException{
        Game game2;
        try{
            game2 = gameDAO.getGame(gameId);
        }
        catch(PersistenceException e) {
            throw new NotFoundException("ERROR: Couldn't get the solicited game.");
        }
        return Math.round(game2.getCurrencyCount());
    }

    public void increaseCurrency() {
        game.increaseCurrency();
    }

    public void updateCurrency(int nCurrencies) throws BusinessException {
        game.updateCurrency(nCurrencies);
        try {
            gameDAO.updateGame(this.game);
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public void updateImprovement(String improvement) throws GeneratorAddedException {
        int index;
        int cost;

        switch (improvement) {
            case "Pills":
                index = 0;
                cost = 100;
                break;
            case "Glasses":
                index = 1;
                cost = 200;
                break;
            case "Carlos":
                index = 2;
                cost = 300;
                break;
            default:
                throw new GeneratorAddedException("Unknown improvement type: " + improvement);
        }

        try {
            updateGeneratorAtIndex(index, cost);
        } catch (PersistenceException e) {
            throw new GeneratorAddedException("Error updating generator with id <" + game.getGameGenerators().get(index).getIdGenerator() + ">. " + e.getMessage());
        }
    }

    private void updateGeneratorAtIndex(int index, int cost) throws GeneratorAddedException, PersistenceException {
        if (game.getGameGenerators().size() <= index || game.getGameGenerators().get(index).getNGens() <= 0) {
            throw new GeneratorAddedException("No generators of the specified type found at index " + index + ".");
        }

        if (game.getCurrencyCount() < cost) {
            throw new GeneratorAddedException("Not enough currency to update generator with id <" + game.getGameGenerators().get(index).getIdGenerator() + ">");
        }

        game.substractCurrency(cost);
        game.getGameGenerators().get(index).incrementImprovementLevel();
        generatorManager.updateGenerator(game.getGameGenerators().get(index));
    }


    public void saveGame(boolean finished) throws PersistenceException {
        game.setFinished(finished);
        gameDAO.updateGame(game);
    }

    public List<Integer> getStatsFromGame(String id) throws BusinessException {
        try {
            return gameDAO.getGameStatistics(id);
        } catch (PersistenceException e) {
            throw new BusinessException("Could not fetch game with id " + id);
        }
    }
}
