package Business;

import Business.Entities.Game;
import Business.Entities.Generator.Generator;
import Business.Exception.BusinessException;
import Business.Exception.GeneratorException.NoGeneratorException;
import Business.Exception.BusinessException;
import Persistance.DAO.GameDAO;
import Persistance.Exception.ConnectionErrorException;
import Persistance.Exception.NotFoundException;
import Persistance.Exception.PersistenceException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*Class to manage the different entities regarding the Game
 *  @Currency, @ImprovementStore, @Game
 */
public class GameManager extends Thread{
    GeneratorManager generatorManager;
    GameDAO gameDAO;
    Game game;
    int threadCount;
    public GameManager (GameDAO gameDAO, GeneratorManager generatorManager) {
        this.gameDAO = gameDAO;
        this.generatorManager = generatorManager;
        threadCount = 0;
        //this.game = new Game();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        game.generatorsProduction();
        threadCount++;

    }

    public void initGame(int gameId, int n_currencies, String user) throws BusinessException{
        List<Generator> generators;

        this.game = new Game(gameId, n_currencies, false, user);
        try {
            generators = generatorManager.getGenerators(gameId);
            for(Generator g: generators) {
                game.initGenerator(g);
            }
        } catch (NoGeneratorException e) {

        }
    }
    public int getGameId() {
        return game.getIdGame();
    }

    /**
     * Get del valor de currency que es te en temps real de la partida, no consulta la base de dades.
     * @return
     */
    public int getGameCurrency() {return Math.round(game.getCurrencyCount());}
    public boolean buyGenerator(String type) throws BusinessException {
        int generatorId;
        if (generatorManager.generatorPurchaseAvailable(game.getCurrencyCount(), game.getIdGame(), type)) {
            generatorId = generatorManager.purchaseNewGenerator(type, game.getIdGame());
            game.addGeneratorToGame(type, generatorId);
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
        game = new Game (id, currency_count, finished, mail_user);
        /*try {
            gameDAO.addGame(new Game(id, currency_count, finished, mail_user));
        }catch (PersistenceException exception) {
            throw new PersistenceException("ERROR: Couldn't add game to the database.");
        }*/
    }
    public Map<Integer, Integer> getUnfinishedGames (String mail_user) throws PersistenceException {
        List<Game> games;
        Map<Integer, Integer> creditsAndIds = new HashMap<>();;
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

    public void increaseCurrency() throws BusinessException{
        game.increaseCurrency();
        try {
            gameDAO.updateGame(this.game);
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
    }
}
