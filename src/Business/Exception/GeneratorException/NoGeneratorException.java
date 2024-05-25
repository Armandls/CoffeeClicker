package Business.Exception.GeneratorException;

/**
 * Excepción lanzada cuando no se encuentra un generador.
 */
public class NoGeneratorException extends GeneratorException {

    /**
     * Constructor de la clase NoGeneratorException.
     * @param message Mensaje de error asociado a la excepción.
     */
    public NoGeneratorException(String message) {
        super(message);
    }
}
