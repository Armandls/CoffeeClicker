package Persistance.SQL;

import Business.Entities.User;
import Persistance.DAO.UserDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUserDAO implements UserDAO {

    @Override
    public void addUser(User user) {
        String query = "INSERT INTO User(nickname, mail, password) VALUES ('" +
                user.getNickname() + "', '" +
                user.getEmail() + "', '" +
                user.getPassword() + "');";
        SQLConnector.getInstance().insertQuery(query);
    }

    @Override
    public User getUser(String email) {
        String query = "SELECT u.nickname, u.mail, u.password FROM User AS u WHERE u.mail = \"" + email + "\" ;";
        ResultSet result = SQLConnector.getInstance().selectQuery(query);

        try {
            if (result.next()) {
                return new User(result.getString(1),
                        result.getString(2),
                        result.getString(3));
            }
            else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteUser(String email) {
        String query = "DELETE FROM User WHERE mail = '" + email + "';";
        return SQLConnector.getInstance().deleteQuery(query);
    }
}
