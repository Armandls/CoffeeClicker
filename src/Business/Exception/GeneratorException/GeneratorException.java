package Business.Exception.GeneratorException;

import Business.Exception.BusinessException;

/**
 * Clase base para las excepciones relacionadas con los generadores.
 */
public class GeneratorException extends BusinessException {

    /**
     * Constructor de la clase GeneratorException.
     * @param message Mensaje de error asociado a la excepción.
     */
    public GeneratorException(String message) {
        super(message);
    }
}
