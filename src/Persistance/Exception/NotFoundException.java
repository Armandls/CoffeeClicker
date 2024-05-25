package Persistance.Exception;

/**
 * Exception class for situations where an entity or resource is not found.
 */
public class NotFoundException extends PersistenceException {

    /**
     * Constructs a new NotFoundException with the specified detail message.
     * @param message The detail message.
     */
    public NotFoundException(String message) {
        super(message);
    }
}
