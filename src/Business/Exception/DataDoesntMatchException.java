package Business.Exception;

import Business.Exception.UserException.UserException;

/**
 * Excepción lanzada cuando los datos proporcionados no coinciden con los esperados.
 */
public class DataDoesntMatchException extends UserException {

    /**
     * Constructor de la clase DataDoesntMatchException.
     * @param message Mensaje de error asociado a la excepción.
     */
    public DataDoesntMatchException(String message) {
        super(message);
    }
}
