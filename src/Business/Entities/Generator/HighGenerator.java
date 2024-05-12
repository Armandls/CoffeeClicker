package Business.Entities.Generator;

import Business.Entities.Improvement.HighImprovement;
import Business.Entities.Improvement.Improvement;

/*Class that implements the abstract class Generator, it represents the best type of Generator
@Generator, @BasicGenerator, @MidGenerator
*/
public class HighGenerator extends Generator{
    private static final String CEUS = "/files/Resources/Images/ceus.png";
    public HighGenerator(int id_generator, int n_currencies, int id_game, int n_gens, Improvement improvement, String imageUrl) {
        super(id_generator, n_currencies, id_game, n_gens, improvement, imageUrl);
    }
    public HighGenerator(int id_game) {
        super(id_game, new HighImprovement());
    }
    public HighGenerator() {super();}
    @Override
    public int getGeneratorPrice() {
        return (int) Math.round(2000 * Math.pow(1.12, super.getNGens()));
    }
    public float generateCurrency() {
        super.increaseCurrency(15 * super.getNGens());
        return 15 * super.getNGens();
    }
    public static String getGeneratorImage() {return CEUS;}
}
