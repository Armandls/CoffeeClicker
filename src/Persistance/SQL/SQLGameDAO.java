package Persistance.SQL;

import Business.Entities.Game;
import Persistance.DAO.GameDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLGameDAO implements GameDAO {
    @Override
    public void addGame(Game game) {
        String query = "INSERT INTO Game(id_user, nickname, mail, password, id_game) VALUES ('" +
                game.getIdGame() + "', '" +
                game.getCurrencyCount() + "', '" +
                "');";
        SQLConnector.getInstance().insertQuery(query);
    }

    @Override
    public Game getGameFromUser(int id_user) {
        String query = "SELECT g.id_game, g.currency_count FROM Game AS g, User AS u WHERE u.id_game = g.id_game AND " +
                        "u.id_user = " + id_user + ";";
        ResultSet result = SQLConnector.getInstance().selectQuery(query);

        try {
            return new Game(result.getInt(1),
                    result.getInt(2));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateGame(int id_game, int currency_count) {
        String query = "UPDATE Game SET currency_count = '" + currency_count + "' WHERE id_game = '" + id_game + "';";
        SQLConnector.getInstance().updateQuery(query);
    }
}
