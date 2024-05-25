package Presentation.Interfaces;

/**
 * Interface for StoresController.
 */
public interface StoresControllerI {
    /**
     * Swaps the current store panel with the specified store panel.
     *
     * @param name The name of the store panel to swap to.
     */
    void swapStore(String name);

    /**
     * Initiates the purchase of a generator with the specified ID.
     *
     * @param id The ID of the generator to purchase.
     */
    void buyGenerator(String id);

    /**
     * Initiates the update of an improvement with the specified ID.
     *
     * @param id The ID of the improvement to update.
     */
    void updateImprovement(String id);
}
