package Presentation;

import Business.Exception.BusinessException;

/**
 * Interface for controlling frames or panels within the application.
 */
public interface FrameController {

    /**
     * Swaps the current panel with another panel.
     *
     * @param panelName The name of the panel to swap to.
     */
    void swapPanel(String panelName);

    /**
     * Initializes the game with the provided data.
     *
     * @param currency       The initial currency value.
     * @param basicGenerator The quantity of basic generators.
     * @param midGenerator   The quantity of mid-tier generators.
     * @param highGenerator  The quantity of high-tier generators.
     * @param lvlBasicImp    The level of basic improvements.
     * @param lvlMidImp      The level of mid-tier improvements.
     * @param lvlHighImp     The level of high-tier improvements.
     */
    void initializeGame(int currency, int basicGenerator, int midGenerator, int highGenerator, int lvlBasicImp, int lvlMidImp, int lvlHighImp);
}
