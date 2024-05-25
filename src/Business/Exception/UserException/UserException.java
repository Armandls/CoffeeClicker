package Business.Exception.UserException;

import Business.Exception.BusinessException;

/**
 * Clase base para las excepciones relacionadas con los usuarios.
 */
public class UserException extends BusinessException {

    /**
     * Constructor de la clase UserException.
     * @param message Mensaje de error asociado a la excepción.
     */
    public UserException(String message) {
        super(message);
    }
}
