package Business.Entities.Improvement;

/**
 * Clase que implementa la clase abstracta Improvement. Representa la mejora para el MidGenerator.
 * @see Improvement
 * @see MidImprovement
 */
public class MidImprovement extends Improvement {
    private final double multiplier = 1.5; // Multiplicador para la mejora

    /**
     * Constructor de la clase MidImprovement.
     * @param idImprovement Identificador único de la mejora.
     * @param level Nivel inicial de la mejora.
     * @param imageUrl URL de la imagen asociada a la mejora.
     */
    public MidImprovement(int idImprovement, int level, String imageUrl) {
        super(idImprovement, level, imageUrl);
    }

    /**
     * Constructor vacío de la clase MidImprovement.
     * Se utiliza un identificador predeterminado y una ruta de imagen básica.
     */
    public MidImprovement() {
        super(0, "path_basic_image");
    }

    /**
     * Obtiene el multiplicador de la mejora.
     * @return Multiplicador de la mejora.
     */
    public double getMultiplier() {
        return multiplier;
    }
}
