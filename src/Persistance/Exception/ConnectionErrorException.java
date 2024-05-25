package Persistance.Exception;

/**
 * Exception class for connection-related errors in persistence operations.
 */
public class ConnectionErrorException extends PersistenceException {

    /**
     * Constructs a new ConnectionErrorException with the specified detail message.
     * @param message The detail message.
     */
    public ConnectionErrorException(String message) {
        super(message);
    }
}
