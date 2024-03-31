package Persistance.DAO;

import Business.Entities.User;

public interface UserDAO {
    void addUser(User user, int id_game);

    User getUser(String email);

    boolean deleteUser(String email);
}
