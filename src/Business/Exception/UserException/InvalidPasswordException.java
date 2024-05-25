package Business.Exception.UserException;

/**
 * Se utiliza para indicar que la contraseña proporcionada no es válida.
 */
public class InvalidPasswordException extends UserException {

    /**
     * Constructor de la clase InvalidPasswordException.
     * @param message Mensaje de error asociado a la excepción.
     */
    public InvalidPasswordException(String message) {
        super(message);
    }
}
