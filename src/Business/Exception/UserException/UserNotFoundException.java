package Business.Exception.UserException;

/**
 * Excepción lanzada cuando no se encuentra un usuario.
 */
public class UserNotFoundException extends UserException {

    /**
     * Constructor de la clase UserNotFoundException.
     * @param message Mensaje de error asociado a la excepción.
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
