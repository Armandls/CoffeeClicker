package Business.Entities.Generator;

import Business.Entities.Improvement.HighImprovement;
import Business.Entities.Improvement.Improvement;

/**
 * Clase que implementa la clase abstracta Generator. Representa el mejor tipo de generador.
 * @see Generator
 * @see BasicGenerator
 * @see MidGenerator
 */
public class HighGenerator extends Generator {
    private static final String CEUS = "/files/Resources/Images/ceus.png"; // Ruta de la imagen del generador

    /**
     * Constructor de la clase HighGenerator.
     * @param id_generator Identificador único del generador.
     * @param n_currencies Cantidad inicial de monedas generadas por este generador.
     * @param id_game Identificador del juego al que pertenece el generador.
     * @param n_gens Número inicial de generadores de este tipo en posesión del jugador.
     * @param improvement Mejora inicial aplicada al generador.
     * @param imageUrl URL de la imagen asociada al generador.
     */
    public HighGenerator(int id_generator, float n_currencies, int id_game, int n_gens, Improvement improvement, String imageUrl) {
        super(id_generator, n_currencies, id_game, n_gens, improvement, imageUrl);
    }

    /**
     * Constructor de la clase HighGenerator con el identificador del juego.
     * @param id_game Identificador del juego al que pertenece el generador.
     */
    public HighGenerator(int id_game) {
        super(id_game, new HighImprovement());
    }

    /**
     * Constructor vacío de la clase HighGenerator.
     */
    public HighGenerator() {
        super();
    }

    /**
     * Obtiene el precio del generador. El precio se calcula utilizando una fórmula exponencial basada en el número de generadores.
     * @return Precio del generador.
     */
    @Override
    public float getGeneratorPrice() {
        return (float) (2000 * Math.pow(1.12, super.getNGens()));
    }

    /**
     * Genera moneda incrementando la cantidad generada por el número de generadores.
     * @return Cantidad de moneda generada.
     */
    @Override
    public float generateCurrency() {
        super.increaseCurrency(15 * super.getNGens());
        return 15 * super.getNGens();
    }

    /**
     * Obtiene la imagen asociada al generador.
     * @return URL de la imagen asociada al generador.
     */
    public String getGeneratorImage() {
        return CEUS;
    }

    /**
     * Obtiene la producción por segundo del generador.
     * @return Producción por segundo.
     */
    public float getProductionPerSec() {
        return (float) (15 * getNGens());
    }
}
