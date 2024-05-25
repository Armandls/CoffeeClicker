package Business.Exception;

/**
 * Clase base para las excepciones de negocios.
 */
public class BusinessException extends Exception {

    /**
     * Constructor de la clase BusinessException.
     * @param message Mensaje de error asociado a la excepción.
     */
    public BusinessException(String message) {
        super(message);
    }
}
