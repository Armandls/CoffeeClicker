package Persistance.DAO;

import Business.Entities.Game;

import java.util.List;

public interface GameDAO {
    void addGame(Game game, int id_user);
    List<Game> getGamesFromUser(int id_user);
    List<Integer> getGameStatistics(int id_game);
    void updateGame(int id_game, int currency_count);
}
