package Business.Exception.GeneratorException;

/**
 * Excepción lanzada cuando no hay suficiente moneda para realizar una acción relacionada con un generador.
 */
public class NotEnoughCurrencyException extends GeneratorException {

    /**
     * Constructor de la clase NotEnoughCurrencyException.
     * @param message Mensaje de error asociado a la excepción.
     */
    public NotEnoughCurrencyException(String message) {
        super(message);
    }
}
