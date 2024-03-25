package Persistance.SQL;

import Business.Entities.User;
import Persistance.UserDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUserDAO implements UserDAO {

    @Override
    public void addUser(User user, int id_game) {
        String query = "INSERT INTO Course(id_user, nickname, mail, password, id_game) VALUES ('" +
                user.getIdUser() + "', '" +
                user.getNickname() + "', '" +
                user.getEmail() + "', '" +
                user.getPassword() + "', '" +
                id_game +
                "');";

        SQLConnector.getInstance().insertQuery(query);
    }

    @Override
    public User getUser(String email) {
        String query = "SELECT u.id_user, u.nickname, u.mail, u.password FROM User AS u WHERE u.mail = " + email + ";";
        ResultSet result = SQLConnector.getInstance().selectQuery(query);

        try {
            return new User(result.getInt(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteUser(String email) {
        String query = "DELETE FROM User WHERE email = '" + email + "';";
        return SQLConnector.getInstance().deleteQuery(query);
    }
}
