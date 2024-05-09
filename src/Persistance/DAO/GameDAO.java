package Persistance.DAO;

import Business.Entities.Game;
import Business.Entities.User;
import Persistance.Exception.ConnectionErrorException;
import Persistance.Exception.PersistenceException;

import java.util.List;

public interface GameDAO {

    List<Game> getUnfinishedGamesFromUser(String mail_user) throws PersistenceException;
    /**
     *
     * @param game
     * @param mail_user
     * @return Retorna el valor del mail_user que s'ha autogenerat al afegir les dades en la taula de Game en la BBDD.
     * @throws ConnectionErrorException
     */
    int addGame(Game game) throws ConnectionErrorException;
    List<Game> getGamesFromUser(User user) throws PersistenceException;
    List<Integer> getGameStatistics(Game game) throws PersistenceException;
    void updateGame(Game game) throws PersistenceException;
    Game getGame(int gameId) throws PersistenceException;
}