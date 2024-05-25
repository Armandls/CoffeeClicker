package Persistance.SQL;

import Business.Entities.User;
import Persistance.DAO.UserDAO;
import Persistance.Exception.ConnectionErrorException;
import Persistance.Exception.NotFoundException;
import Persistance.Exception.PersistenceException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementation of the UserDAO interface for SQL databases.
 */
public class SQLUserDAO implements UserDAO {

    /**
     * Adds a new user to the database.
     *
     * @param user The user to add.
     * @throws ConnectionErrorException If there is an error connecting to the database.
     */
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

    /**
     * Retrieves a user from the database based on email.
     *
     * @param email The email of the user to retrieve.
     * @return The retrieved user.
     * @throws NotFoundException      If the user with the specified email is not found in the database.
     * @throws ConnectionErrorException If there is an error connecting to the database.
     */
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

    /**
     * Deletes a user from the database based on email.
     *
     * @param email The email of the user to delete.
     * @throws ConnectionErrorException If there is an error connecting to the database.
     */
    @Override
    public void deleteUser(String email) throws ConnectionErrorException {
        String query = "DELETE FROM User WHERE mail = '" + email + "';";
        try  {
            SQLConnector.getInstance().deleteQuery(query);
        } catch (ConnectionErrorException e) {
            throw new ConnectionErrorException("Error deleting user with email <" + email + ">" + e.getMessage());
        }
    }

    /**
     * Updates a user's information in the database.
     *
     * @param user The user with updated information.
     * @throws ConnectionErrorException If there is an error connecting to the database.
     */
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

    /**
     * Checks if a username is available in the database.
     *
     * @param username The username to check.
     * @return True if the username is available, false otherwise.
     * @throws ConnectionErrorException If there is an error connecting to the database.
     */
    public boolean isUsernameAvailable(String username) throws ConnectionErrorException {
        String query = "SELECT u.nickname FROM User AS u WHERE u.nickname = \"" + username + "\" ;";
        ResultSet result = SQLConnector.getInstance().selectQuery(query);

        try {
            // Username does not exist
            return !result.next(); // Username exists
        } catch (SQLException e) {
            throw new ConnectionErrorException(e.getMessage());
        }
    }
}
