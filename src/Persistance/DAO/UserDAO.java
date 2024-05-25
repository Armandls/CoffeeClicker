package Persistance.DAO;

import Business.Entities.User;
import Persistance.Exception.ConnectionErrorException;
import Persistance.Exception.PersistenceException;

/**
 * Interface for accessing user data in the database.
 */
public interface UserDAO {

    /**
     * Adds a new user to the database.
     * @param user The user to be added.
     * @throws ConnectionErrorException If an error occurs while connecting to the database.
     */
    void addUser(User user) throws ConnectionErrorException;

    /**
     * Retrieves a user from the database based on their email.
     * @param email The email of the user to retrieve.
     * @return The user object.
     * @throws PersistenceException If an error occurs during the data retrieval process.
     */
    User getUser(String email) throws PersistenceException;

    /**
     * Deletes a user from the database based on their email.
     * @param email The email of the user to delete.
     * @throws ConnectionErrorException If an error occurs while connecting to the database.
     */
    void deleteUser(String email) throws ConnectionErrorException;

    /**
     * Updates an existing user in the database.
     * @param user The user to be updated.
     * @throws ConnectionErrorException If an error occurs while connecting to the database.
     */
    void updateUser(User user) throws ConnectionErrorException;

    /**
     * Checks if a username is available in the database.
     * @param username The username to check.
     * @return True if the username is available, false otherwise.
     * @throws ConnectionErrorException If an error occurs while connecting to the database.
     */
    boolean isUsernameAvailable(String username) throws ConnectionErrorException;
}
