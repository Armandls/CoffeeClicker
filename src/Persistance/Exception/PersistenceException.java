package Persistance.Exception;

/**
 * Base exception class for persistence-related exceptions.
 */
public class PersistenceException extends Exception {

    /**
     * Constructs a new PersistenceException with the specified detail message.
     * @param message The detail message.
     */
    public PersistenceException(String message) {
        super(message);
    }
}
