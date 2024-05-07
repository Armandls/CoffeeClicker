package Business;

import Business.Entities.Game;
import Persistance.DAO.GameDAO;
import Persistance.Exception.NotFoundException;
import Persistance.Exception.PersistenceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*Class to manage the different entities regarding the Game
 *  @Currency, @ImprovementStore, @Game
 */
public class GameManager {
    GameDAO gameDAO;
    public GameManager (GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }
    public void addGame(Game game, String mail_user) throws PersistenceException {
        try {
            gameDAO.addGame(game, mail_user);
        }catch (PersistenceException exception) {
            throw new PersistenceException("ERROR: Couldn't add game to the database.");
        }
    }
    public Map<Integer, Integer> getUnfinishedGames (String mail_user) throws PersistenceException {
        List<Game> games;
        Map<Integer, Integer> creditsAndIds = null;
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
