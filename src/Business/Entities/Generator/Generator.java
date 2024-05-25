package Business.Entities.Generator;

import Business.Entities.Improvement.Improvement;

/**
 * The {@code Generator} class represents an abstract generator that produces a certain
 * amount of currency over time. This class provides methods to manage generators,
 * increase currency, and handle improvements.
 *
 * <p>
 * Note: This class is abstract and should be subclassed to provide specific generator
 * implementations.
 * </p>
 *
 * @see Business.Entities.Improvement.Improvement
 */
public abstract class Generator {
    private int id_generator;
    private int n_gens;
    private float n_currencies;
    private int id_game;
    private Improvement improvement;
    private String imageUrl;

    /**
     * Constructs a new {@code Generator} with the specified parameters.
     *
     * @param id_generator the unique identifier of the generator
     * @param n_currencies the initial amount of currencies produced by the generator
     * @param id_game      the identifier of the game this generator belongs to
     * @param n_gens       the number of generators
     * @param improvement  the improvement associated with this generator
     * @param imageUrl     the URL of the generator's image
     */
    public Generator(int id_generator, float n_currencies, int id_game, int n_gens, Improvement improvement, String imageUrl) {
        this.id_generator = id_generator;
        this.n_currencies = n_currencies;
        this.id_game = id_game;
        this.n_gens = n_gens;
        this.improvement = improvement;
        this.imageUrl = imageUrl;
    }

    /**
     * Constructs a new {@code Generator} with the specified parameters.
     *
     * @param id_generator the unique identifier of the generator
     * @param n_currencies the initial amount of currencies produced by the generator
     * @param id_game      the identifier of the game this generator belongs to
     * @param n_gens       the number of generators
     * @param improvement  the improvement associated with this generator
     * @param imageUrl     the URL of the generator's image
     * @param description  a description of the generator
     */
    public Generator(int id_generator, float n_currencies, int id_game, int n_gens, Improvement improvement, String imageUrl, String description) {
        this(id_generator, n_currencies, id_game, n_gens, improvement, imageUrl);
    }

    /**
     * Constructs a new {@code Generator} with the specified game ID.
     *
     * @param id_game the identifier of the game this generator belongs to
     */
    public Generator(int id_game) {
        this.id_game = id_game;
        this.n_gens = 1;
        this.n_currencies = 0;
    }

    /**
     * Constructs a new {@code Generator} with the specified game ID and improvement.
     *
     * @param id_game     the identifier of the game this generator belongs to
     * @param improvement the improvement associated with this generator
     */
    public Generator(int id_game, Improvement improvement) {
        this(id_game);
        this.improvement = improvement;
    }

    /**
     * Constructs a new {@code Generator} with the specified game ID and image URL.
     *
     * @param id_game  the identifier of the game this generator belongs to
     * @param imageUrl the URL of the generator's image
     */
    public Generator(int id_game, String imageUrl) {
        this(id_game);
        this.imageUrl = imageUrl;
    }

    /**
     * Constructs a new {@code Generator} with no parameters.
     * This constructor is typically used to create an empty generator instance
     * for identifying its type.
     */
    public Generator() {
        this.n_gens = 0;
    }

    /**
     * Generates currency. This method should be overridden by subclasses to
     * provide specific currency generation logic.
     *
     * @return the amount of currency generated
     */
    public float generateCurrency() {
        return 0;
    }

    /**
     * Increases the amount of currency produced by this generator.
     *
     * @param increaseVal the value by which to increase the currency
     */
    public void increaseCurrency(float increaseVal) {
        n_currencies += increaseVal;
    }

    /**
     * Returns the identifier of this generator.
     *
     * @return the identifier of this generator
     */
    public int getIdGenerator() {
        return id_generator;
    }

    /**
     * Sets the identifier of this generator.
     *
     * @param id_generator the new identifier for this generator
     */
    public void setIdGenerator(int id_generator) {
        this.id_generator = id_generator;
    }

    /**
     * Sets the identifier of the improvement associated with this generator.
     *
     * @param id the new improvement identifier
     */
    public void setIdImprovement(int id) {
        this.improvement.setIdImprovement(id);
    }

    /**
     * Returns the identifier of the game this generator belongs to.
     *
     * @return the game identifier
     */
    public int getIdGame() {
        return id_game;
    }

    /**
     * Returns the number of generators.
     *
     * @return the number of generators
     */
    public int getNGens() {
        return n_gens;
    }

    /**
     * Returns the improvement associated with this generator.
     *
     * @return the improvement
     */
    public Improvement getImprovement() {
        return improvement;
    }

    /**
     * Sets the improvement associated with this generator.
     *
     * @param improvement the new improvement
     */
    public void setImprovement(Improvement improvement) {
        this.improvement = improvement;
    }

    /**
     * Returns the amount of currencies produced by this generator.
     *
     * @return the amount of currencies
     */
    public float getNCurrencies() {
        return n_currencies;
    }

    /**
     * Returns the price of this generator. This method should be overridden
     * by subclasses to provide specific price calculation.
     *
     * @return the price of the generator
     */
    public float getGeneratorPrice() {
        return 0;
    }

    /**
     * Increases the number of generators by one.
     */
    public void addGenerator() {
        n_gens++;
    }

    /**
     * Returns the URL of the generator's image.
     * This method should be overridden by subclasses to provide the image URL.
     *
     * @return the URL of the generator's image
     */
    public String getGeneratorImage() {
        return null;
    }

    /**
     * Increments the improvement level associated with this generator.
     */
    public void incrementImprovementLevel() {
        improvement.incrementImprovementLevel();
    }

    /**
     * Returns the production per second of this generator.
     * This method should be overridden by subclasses to provide specific production logic.
     *
     * @return the production per second
     */
    public float getProductionPerSec() {
        return 0;
    }

    /**
     * Calculates the production percentage based on the total currency.
     *
     * @param totalCurrency the total currency
     * @return the production percentage
     */
    public float getProductionPercentage(float totalCurrency) {
        return (getProductionPerSec() / totalCurrency) * 100;
    }
}
