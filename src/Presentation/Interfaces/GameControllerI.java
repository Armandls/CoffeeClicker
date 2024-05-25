package Presentation.Interfaces;

import Persistance.Exception.ConnectionErrorException;
import Persistance.Exception.PersistenceException;
import java.awt.Point;

/**
 * Interface for GameController.
 */
public interface GameControllerI {
    /**
     * Swaps the current panel with the specified panel.
     *
     * @param panel The panel to swap to.
     */
    void swapPanel(String panel);

    /**
     * Shows the user profile.
     */
    void showProfile();

    /**
     * Shows the user configuration.
     */
    void showConfig();

    /**
     * Toggles the store.
     */
    void toggleStore();

    /**
     * Starts the red panel animation.
     *
     * @param p The point to start the animation.
     */
    void startRedPanelAnimation(Point p);

    /**
     * Hides the user profile.
     */
    void hideProfile();

    /**
     * Hides the user configuration.
     */
    void hideConfig();

    /**
     * Deletes the user account.
     *
     * @throws ConnectionErrorException if a connection error occurs.
     */
    void deleteUser() throws ConnectionErrorException;

    /**
     * Restarts the user values.
     */
    void restartValuesUser();

    /**
     * Saves the game.
     *
     * @param b The boolean value to save the game.
     * @throws PersistenceException if a persistence error occurs.
     */
    void saveGame(boolean b) throws PersistenceException;

    /**
     * Fetches the games.
     */
    void fetchGames();

    /**
     * Logs out the user.
     */
    void logout();

    /**
     * Exits the game.
     *
     * @throws PersistenceException if a persistence error occurs.
     */
    void exit();

    /**
     * Restarts the game.
     */
    void restartGame();
}
