package Persistance.SQL;

import Business.Entities.Game;
import Business.Entities.User;
import Persistance.DAO.GameDAO;
import Persistance.Exception.ConnectionErrorException;
import Persistance.Exception.PersistenceException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLGameDAO implements GameDAO {
    @Override
    public int addGame(Game game,int id_user) throws ConnectionErrorException {
        try {
            String query = "INSERT INTO Game(currency_count, id_user) VALUES ('" +
                    game.getCurrencyCount() + "', '" +
                    id_user + "');";
            return SQLConnector.getInstance().insertQueryAutogeneratedKey(query);
        } catch (ConnectionErrorException e) {
            throw new ConnectionErrorException("Error adding game. " + e.getMessage());
        }
    }

    @Override
    public List<Game> getGamesFromUser(User user) throws ConnectionErrorException{
        ArrayList<Game> games = new ArrayList<>();
        String query = "SELECT g.id_game, g.currency_count FROM Game AS g, User AS u WHERE u.id_game = g.id_game AND " +
                        "u.id_user = " + user.getEmail() + ";";
        ResultSet result = SQLConnector.getInstance().selectQuery(query);

        try {
            while (result.next()) {
                Game aux = new Game(result.getInt(1),
                        result.getInt(2));
                games.add(aux);
            }
            return games;
        } catch (SQLException e) {
            throw new ConnectionErrorException(e.getMessage());
        }
    }

    @Override
    public List<Integer> getGameStatistics(Game game) throws PersistenceException{

        ArrayList<Integer> currencies = new ArrayList<>();
        String query = "SELECT s.current_currency FROM Game AS g, Statistics AS s WHERE g.id_game = s.id_game AND " +
                "g.id_user = " + game.getIdGame() + " ORDER BY s.game_min ASC" +";";
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
    public void updateGame(Game game) throws ConnectionErrorException{
        String query = "UPDATE Game SET currency_count = '" + game.getCurrencyCount() + "' WHERE id_game = '" + game.getIdGame() + "';";
        SQLConnector.getInstance().updateQuery(query);
    }
}
