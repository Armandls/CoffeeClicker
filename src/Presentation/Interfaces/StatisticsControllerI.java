package Presentation.Interfaces;

import Persistance.Exception.ConnectionErrorException;
import Persistance.Exception.PersistenceException;

/**
 * Interface for StatisticsController.
 */
public interface StatisticsControllerI {
    /**
     * Swaps the current panel with the specified panel.
     *
     * @param name The name of the panel to swap to.
     */
    void swapPanel(String name);

    /**
     * Displays the statistics of the game with the specified ID.
     *
     * @param id The ID of the game to display statistics for.
     */
    void displayGameStats(String id);

    /**
     * Logs out the user.
     */
    void logout();

    /**
     * Deletes the user account.
     *
     * @throws ConnectionErrorException if a connection error occurs.
     */
    void deleteUser() throws ConnectionErrorException;

    /**
     * Restarts the game.
     */
    void restartGame();

    /**
     * Restarts the user values.
     */
    void restartValuesUser();
}
