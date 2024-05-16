package Business;

import Business.Entities.Game;
import Business.Entities.Generator.Generator;
import Business.Exception.BusinessException;
import Business.Exception.GeneratorException.NoGeneratorException;
import Business.Exception.BusinessException;
import Persistance.DAO.GameDAO;
import Persistance.Exception.NotFoundException;
import Persistance.Exception.PersistenceException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*Class to manage the different entities regarding the Game
 *  @Currency, @ImprovementStore, @Game
 */
public class GameManager {
    GeneratorManager generatorManager;
    GameDAO gameDAO;
    Game game;
    public GameManager (GameDAO gameDAO, GeneratorManager generatorManager) {
        this.gameDAO = gameDAO;
        this.generatorManager =generatorManager;
        //this.game = new Game();
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
    public int getGameCurrency() {return game.getCurrencyCount();}
    public boolean buyGenerator(String type) throws BusinessException {
        int generatorId;
        if (generatorManager.generatorPurchaseAvailable(game.getCurrencyCount(), game.getIdGame(), type)) {
            generatorId = generatorManager.purchaseNewGenerator(type, game.getIdGame());
            game.addGeneratorToGame(type, generatorId);
            return true;
        }
        return false;
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
            creditsAndIds.put(game.getIdGame(), game.getCurrencyCount());
        }

        return creditsAndIds;
    }

    public int getGameCurrencies(int gameId) throws NotFoundException{
        Game game;
        try{
            game = gameDAO.getGame(gameId);
        }
        catch(PersistenceException e) {
            throw new NotFoundException("ERROR: Couldn't get the solicited game.");
        }
        return game.getCurrencyCount();
    }
}
