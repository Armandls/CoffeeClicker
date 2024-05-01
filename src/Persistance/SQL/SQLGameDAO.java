package Persistance.SQL;

import Business.Entities.Game;
import Persistance.DAO.GameDAO;
import Persistance.Exception.PersistenceException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLGameDAO implements GameDAO {
    @Override
    public void addGame(Game game,int id_user) throws PersistenceException {
        String query = "INSERT INTO Game(id_game, currency_count, id_user) VALUES ('" +
                game.getIdGame() + "', '" +
                game.getCurrencyCount() + "', '" +
                id_user + "');";
        SQLConnector.getInstance().insertQuery(query);
    }

    @Override
    public List<Game> getGamesFromUser(int id_user) throws PersistenceException{
        ArrayList<Game> games = new ArrayList<>();
        String query = "SELECT g.id_game, g.currency_count FROM Game AS g, User AS u WHERE u.id_game = g.id_game AND " +
                        "u.id_user = " + id_user + ";";
        ResultSet result = SQLConnector.getInstance().selectQuery(query);

        try {
            while (result.next()) {
                Game aux = new Game(result.getInt(1),
                        result.getInt(2));
                games.add(aux);
            }
            return games;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Integer> getGameStatistics(int id_game) throws PersistenceException{

        ArrayList<Integer> currencies = new ArrayList<>();
        String query = "SELECT s.current_currency FROM Game AS g, Statistics AS s WHERE g.id_game = s.id_game AND " +
                "g.id_user = " + id_game + " ORDER BY s.game_min ASC" +";";
        ResultSet result = SQLConnector.getInstance().selectQuery(query);

        try {
            while (result.next()) {
                currencies.add(result.getInt(1));
            }
            return currencies;
        } catch (SQLException e) {
            throw new PersistenceException(e.getMessage());
        }

    }
    @Override
    public void updateGame(int id_game, int currency_count) throws PersistenceException{
        String query = "UPDATE Game SET currency_count = '" + currency_count + "' WHERE id_game = '" + id_game + "';";
        SQLConnector.getInstance().updateQuery(query);
    }
}
