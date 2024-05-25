package Persistance.DAO;

import Business.Entities.Game;
import Business.Entities.User;
import Persistance.Exception.ConnectionErrorException;
import Persistance.Exception.PersistenceException;

import java.util.List;

/**
 * Interface for accessing game data in the database.
 */
public interface GameDAO {

    /**
     * Retrieves a list of unfinished games associated with a user.
     * @param mail_user The email of the user.
     * @return A list of unfinished games.
     * @throws PersistenceException If an error occurs during the data retrieval process.
     */
    List<Game> getUnfinishedGamesFromUser(String mail_user) throws PersistenceException;

    /**
     * Retrieves a list of finished games associated with a user.
     * @param mail_user The email of the user.
     * @return A list of finished games.
     * @throws PersistenceException If an error occurs during the data retrieval process.
     */
    List<Game> getFinishedGamesFromUser(String mail_user) throws PersistenceException;

    /**
     * Adds a new game to the database.
     * @param game The game to be added.
     * @return The ID of the user associated with the game.
     * @throws ConnectionErrorException If an error occurs while connecting to the database.
     */
    int addGame(Game game) throws ConnectionErrorException;

    /**
     * Retrieves game statistics.
     * @param game The game.
     * @return A list of game statistics.
     * @throws PersistenceException If an error occurs during the data retrieval process.
     */
    List<Integer> getGameStatistics(String game) throws PersistenceException;

    /**
     * Updates the information of a game in the database.
     * @param game The game to be updated.
     * @throws PersistenceException If an error occurs during the data update process.
     */
    void updateGame(Game game) throws PersistenceException;

    /**
     * Retrieves a game based on its ID.
     * @param gameId The ID of the game.
     * @return The game corresponding to the ID.
     * @throws PersistenceException If an error occurs during the data retrieval process.
     */
    Game getGame(int gameId) throws PersistenceException;

    /**
     * Adds statistics for a game.
     * @param gameId The ID of the game.
     * @param gameMin The game duration in minutes.
     * @param currentCurrency The current currency in the game.
     * @throws ConnectionErrorException If an error occurs while connecting to the database.
     */
    void addStatistic(int gameId, int gameMin, int currentCurrency) throws ConnectionErrorException;
}
