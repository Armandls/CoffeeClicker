package Persistance.DAO;

import Business.Entities.Game;

public interface GameDAO {
    void addGame(Game game);
    Game getGameFromUser(int id_user);
    void updateGame(int id_game, int currency_count);
}
