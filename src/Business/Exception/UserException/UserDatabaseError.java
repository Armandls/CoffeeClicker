package Business.Exception.UserException;

/**
 * Excepción lanzada cuando se produce un error en la base de datos relacionado con un usuario.
 */
public class UserDatabaseError extends UserException {

    /**
     * Constructor de la clase UserDatabaseError.
     * @param message Mensaje de error asociado a la excepción.
     */
    public UserDatabaseError(String message) {
        super(message);
    }
}
