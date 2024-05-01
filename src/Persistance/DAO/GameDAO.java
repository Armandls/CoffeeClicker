package Persistance.DAO;

import Business.Entities.Game;
import Persistance.Exception.PersistenceException;

import java.util.List;

public interface GameDAO {
    void addGame(Game game, int id_user) throws PersistenceException;
    List<Game> getGamesFromUser(int id_user) throws PersistenceException;

    List<Game> getUnfinishedGamesFromUser(String mail_user) throws PersistenceException;

    List<Integer> getGameStatistics(int id_game) throws PersistenceException;
    void updateGame(int id_game, int currency_count) throws PersistenceException;
}