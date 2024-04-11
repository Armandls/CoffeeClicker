package Persistance.DAO;

import Business.Entities.User;

public interface UserDAO {
    void addUser(User user);

    User getUser(String email);

    boolean deleteUser(String email);
    void updateUser(User user);
}
