package Business.Exception.UserException;

import Business.Exception.BusinessException;

/**
 * Excepción lanzada cuando se intenta crear un usuario que ya existe.
 */
public class UserAlreadyExistsException extends UserException {

    /**
     * Constructor de la clase UserAlreadyExistsException.
     * @param message Mensaje de error asociado a la excepción.
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
