package Business.Exception.GeneratorException;

/**
 * Excepción lanzada cuando se intenta añadir un generador que ya ha sido añadido previamente.
 */
public class GeneratorAddedException extends Exception {

    /**
     * Constructor de la clase GeneratorAddedException.
     * @param message Mensaje de error asociado a la excepción.
     */
    public GeneratorAddedException(String message) {
        super(message);
    }
}
