package Presentation.Interfaces;

import Business.Exception.BusinessException;
import Persistance.Exception.PersistenceException;

/**
 * Interface for HomeController.
 */
public interface HomeControllerI {

    /**
     * Creates a new game.
     *
     * @throws PersistenceException if a persistence error occurs.
     */
    void createNewGame() throws PersistenceException;

    /**
     * Resumes a game.
     *
     * @throws PersistenceException if a persistence error occurs.
     */
    void resumeGameButton() throws PersistenceException;

    /**
     * Resumes a game.
     *
     * @param gameId The game id to resume.
     * @throws BusinessException if a business error occurs.
     */
    void resumeGame(int gameId) throws BusinessException;

    /**
     * Swaps the current panel with the specified panel.
     *
     * @param panel The panel to swap to.
     */
    void swapPanel(String panel);
}
