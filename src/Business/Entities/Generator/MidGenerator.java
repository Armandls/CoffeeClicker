package Business.Entities.Generator;

import Business.Entities.Improvement.Improvement;
import Business.Entities.Improvement.MidImprovement;

/**
 * Clase que implementa la clase abstracta Generator. Representa un generador que es mejor que el básico
 * pero no tan bueno como el de nivel alto.
 * @see Generator
 * @see BasicGenerator
 * @see HighGenerator
 */
public class MidGenerator extends Generator {
    private static final String NOTES = "/files/Resources/Images/EnchantedBook.gif"; // Ruta de la imagen del generador

    /**
     * Constructor de la clase MidGenerator.
     * @param id_generator Identificador único del generador.
     * @param n_currencies Cantidad inicial de monedas generadas por este generador.
     * @param id_game Identificador del juego al que pertenece el generador.
     * @param n_gens Número inicial de generadores de este tipo en posesión del jugador.
     * @param improvement Mejora inicial aplicada al generador.
     * @param imageUrl URL de la imagen asociada al generador.
     */
    public MidGenerator(int id_generator, float n_currencies, int id_game, int n_gens, Improvement improvement, String imageUrl) {
        super(id_generator, n_currencies, id_game, n_gens, improvement, imageUrl);
    }

    /**
     * Constructor vacío de la clase MidGenerator.
     * Se utiliza para obtener la instancia del generador y determinar su tipo.
     */
    public MidGenerator() {
        super();
    }

    /**
     * Constructor de la clase MidGenerator con el identificador del juego.
     * @param id_game Identificador del juego al que pertenece el generador.
     */
    public MidGenerator(int id_game) {
        super(id_game, new MidImprovement());
    }

    /**
     * Obtiene el precio del generador. El precio se calcula utilizando una fórmula exponencial basada en el número de generadores.
     * @return Precio del generador.
     */
    @Override
    public float getGeneratorPrice() {
        return (float) (150 * Math.pow(1.15, super.getNGens()));
    }

    /**
     * Genera moneda incrementando la cantidad generada por el número de generadores.
     * @return Cantidad de moneda generada.
     */
    @Override
    public float generateCurrency() {
        super.increaseCurrency(super.getNGens());
        return super.getNGens();
    }

    /**
     * Obtiene la imagen asociada al generador.
     * @return URL de la imagen asociada al generador.
     */
    public String getGeneratorImage() {
        return NOTES;
    }

    /**
     * Obtiene la producción por segundo del generador.
     * @return Producción por segundo.
     */
    public float getProductionPerSec() {
        return (float) (getNGens());
    }
}
