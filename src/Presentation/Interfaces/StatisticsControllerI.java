package Presentation.Interfaces;

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
}
