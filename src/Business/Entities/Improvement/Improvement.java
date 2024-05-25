package Business.Entities.Improvement;

/**
 * Clase abstracta para representar los atributos compartidos de las mejoras disponibles para los generadores.
 */
public abstract class Improvement {

    private int id_improvement; // Identificador único de la mejora

    private int level; // Nivel de la mejora

    private String imageUrl; // URL de la imagen asociada a la mejora

    /**
     * Constructor de la clase Improvement.
     * @param id_improvement Identificador único de la mejora.
     * @param level Nivel de la mejora.
     * @param imageUrl URL de la imagen asociada a la mejora.
     */
    public Improvement(int id_improvement, int level, String imageUrl) {
        this.id_improvement = id_improvement;
        this.level = level;
        this.imageUrl = imageUrl;
    }

    /**
     * Constructor de la clase Improvement.
     * @param level Nivel de la mejora.
     * @param imageUrl URL de la imagen asociada a la mejora.
     */
    public Improvement(int level, String imageUrl) {
        this.level = level;
        this.imageUrl = imageUrl;
    }

    /**
     * Obtiene el identificador único de la mejora.
     * @return Identificador único de la mejora.
     */
    public int getIdImprovement() {
        return id_improvement;
    }

    /**
     * Establece el identificador único de la mejora.
     * @param id_improvement Identificador único de la mejora.
     */
    public void setIdImprovement(int id_improvement) {
        this.id_improvement = id_improvement;
    }

    /**
     * Obtiene el nivel de la mejora.
     * @return Nivel de la mejora.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Establece el nivel de la mejora.
     * @param level Nivel de la mejora.
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Obtiene la URL de la imagen asociada a la mejora.
     * @return URL de la imagen asociada a la mejora.
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Establece la URL de la imagen asociada a la mejora.
     * @param imageUrl URL de la imagen asociada a la mejora.
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Incrementa el nivel de la mejora.
     */
    public void incrementImprovementLevel() {
        level++;
    }

    /**
     * Obtiene el multiplicador de la mejora.
     * @return Multiplicador de la mejora.
     */
    public double getMultiplier() {
        return 0;
    }
}
