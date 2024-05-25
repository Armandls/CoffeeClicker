package Business.Entities.Generator;

import Business.Entities.Improvement.BasicImprovement;
import Business.Entities.Improvement.Improvement;

/**
 * Class that implements the abstract class Generator, it represents the most basic type of Generator
 * {@link Generator}, {@link MidGenerator}, {@link HighGenerator}
 */
public class BasicGenerator extends Generator {
    private static final String REDBULL = "/files/Resources/Images/redBull.png";

    /**
     * Constructor of the class BasicGenerator
     *
     * @param id_generator the identifier of the generator
     * @param n_currencies the number of currencies
     * @param id_game the identifier of the game
     * @param n_gens the number of generators
     * @param improvement the improvement associated with the generator
     * @param imageUrl the URL of the image representing the generator
     */
    public BasicGenerator(int id_generator, float n_currencies, int id_game, int n_gens, Improvement improvement, String imageUrl) {
        super(id_generator, n_currencies, id_game, n_gens, improvement, imageUrl);
    }

    /**
     * Default constructor for BasicGenerator
     */
    public BasicGenerator() {
        super();
    }

    /**
     * Constructor for BasicGenerator with game ID
     *
     * @param id_game the identifier of the game
     */
    public BasicGenerator(int id_game) {
        super(id_game, new BasicImprovement());
    }

    /**
     * Calculates the price of the generator
     *
     * @return the price of the generator
     */
    @Override
    public float getGeneratorPrice() {
        return (float) (10 * Math.pow(1.07, super.getNGens()));
    }

    /**
     * Generates currency based on the number of generators
     *
     * @return the amount of currency generated
     */
    @Override
    public float generateCurrency() {
        super.increaseCurrency((float) 0.2 * super.getNGens());
        return (float) 0.2 * super.getNGens();
    }

    /**
     * Gets the image URL of the generator
     *
     * @return the image URL of the generator
     */
    public String getGeneratorImage() {
        return REDBULL;
    }

    /**
     * Gets the production per second based on the number of generators
     *
     * @return the production per second
     */
    public float getProductionPerSec() {
        return (float) (0.2 * getNGens());
    }
}
