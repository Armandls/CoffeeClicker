package Persistance.DAO;

import Business.Entities.Game;
import Business.Entities.User;
import Persistance.Exception.ConnectionErrorException;
import Persistance.Exception.PersistenceException;

import java.util.List;

public interface GameDAO {
    /**
     *
     * @param game
     * @param id_user
     * @return Retorna el valor del id_game que s'ha autogenerat al afegir les dades en la taula de Game en la BBDD.
     * @throws ConnectionErrorException
     */
    int addGame(Game game, int id_user) throws ConnectionErrorException;
    List<Game> getGamesFromUser(User user) throws PersistenceException;
    List<Integer> getGameStatistics(Game game) throws PersistenceException;
    void updateGame(Game game) throws PersistenceException;
}