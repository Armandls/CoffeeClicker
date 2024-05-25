package Business.Exception.UserException;

/**
 * Se utiliza para indicar que el formato del correo electrónico introducido no es correcto. Es diferente de la excepción que avisa si no se encuentra el usuario.
 */
public class InvalidLoginEmailException extends UserException {

    /**
     * Constructor de la clase InvalidLoginEmailException.
     * @param message Mensaje de error asociado a la excepción.
     */
    public InvalidLoginEmailException(String message) {
        super(message);
    }
}
