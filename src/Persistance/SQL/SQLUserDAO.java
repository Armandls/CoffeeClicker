package Persistance.SQL;

import Business.Entities.User;
import Persistance.DAO.UserDAO;
import Persistance.Exception.ConnectionErrorException;
import Persistance.Exception.NotFoundException;
import Persistance.Exception.PersistenceException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUserDAO implements UserDAO {

    @Override
    public void addUser(User user) throws ConnectionErrorException {
        try {
            String query = "INSERT INTO User(nickname, mail, password) VALUES ('" +
                    user.getNickname() + "', '" +
                    user.getEmail() + "', '" +
                    user.getPassword() + "');";
            SQLConnector.getInstance().insertQuery(query);
        } catch (ConnectionErrorException e) {
            throw new ConnectionErrorException("Error adding user with email <" + user.getEmail() + ">. " + e.getMessage());
        }
    }

    @Override
    public User getUser(String email) throws PersistenceException {
        String query = "SELECT u.nickname, u.mail, u.password FROM User AS u WHERE u.mail = \"" + email + "\" ;";
        ResultSet result = SQLConnector.getInstance().selectQuery(query);

        try {
            if (result.next()) {
                return new User(result.getString(1),
                        result.getString(2),
                        result.getString(3));
            }
            else {
                throw new NotFoundException("User with email: <" + email + "> was not found in the database.");
            }
        } catch (SQLException e) {
            throw new ConnectionErrorException(e.getMessage());
        }
    }

    @Override
    public void deleteUser(String email) throws ConnectionErrorException {
        String query = "DELETE FROM User WHERE mail = '" + email + "';";
        try  {
            SQLConnector.getInstance().deleteQuery(query);
        } catch (ConnectionErrorException e) {
            throw new ConnectionErrorException("Error deleting user with email <" + email + ">" + e.getMessage());
        }
    }

    @Override
    public void updateUser(User user) throws ConnectionErrorException{
        String query = "UPDATE User SET " +
                "nickname = '" + user.getNickname() + "', " +
                "password = '" + user.getPassword() + "' " +
                "WHERE mail = '" + user.getEmail() + "';";

        try {
            SQLConnector.getInstance().updateQuery(query);
        } catch (ConnectionErrorException e) {
            throw new ConnectionErrorException("Error updating user with email <" + user.getEmail() + ">. " + e.getMessage());
        }
    }

    public boolean isUsernameAvailable(String username) throws ConnectionErrorException {
        String query = "SELECT u.nickname FROM User AS u WHERE u.nickname = \"" + username + "\" ;";
        ResultSet result = SQLConnector.getInstance().selectQuery(query);

        try {
            if (result.next()) {
                return false; // Username exists
            } else {
                return true; // Username does not exist
            }
        } catch (SQLException e) {
            throw new ConnectionErrorException(e.getMessage());
        }
    }
}
