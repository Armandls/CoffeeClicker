package Persistance.DAO;

import Business.Entities.User;
import Persistance.Exception.ConnectionErrorException;
import Persistance.Exception.PersistenceException;

public interface UserDAO {
    void addUser(User user) throws ConnectionErrorException;

    User getUser(String email) throws PersistenceException;

    void deleteUser(String email) throws ConnectionErrorException;
    void updateUser(User user) throws ConnectionErrorException;
}
