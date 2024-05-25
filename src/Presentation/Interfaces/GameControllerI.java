package Presentation.Interfaces;

import Persistance.Exception.ConnectionErrorException;
import Persistance.Exception.PersistenceException;

import java.awt.*;

public interface GameControllerI {
    void swapPanel(String panel);
    void showProfile();
    void showConfig();
    void toggleStore();
    void startRedPanelAnimation(Point p);
    void hideProfile();
    void hideConfig();
    void deleteUser()  throws ConnectionErrorException;
    void restartValuesUser();
    void saveGame(boolean b) throws PersistenceException;
    void fetchGames();
    void logout();
    void exit();
    void restartGame();
}
