package Business.Entities.Generator;

import Business.Entities.Improvement.Improvement;
import Business.Entities.Improvement.MidImprovement;

/*Class that implements the abstract class Generator, it represents a Generator that is better than the basic one
but not as good as the high
@Generator, @BasicGenerator, @HighGenerator
*/
public class MidGenerator extends Generator {
    private static final String NOTES = "/files/Resources/Images/EnchantedBook.gif";
    public MidGenerator(int id_generator, float n_currencies, int id_game, int n_gens, Improvement improvement, String imageUrl) {
        super(id_generator, n_currencies, id_game, n_gens, improvement, imageUrl);
    }
    //Creació de generador buit per obtenir la instancia del generador i saber quin tipus de fill es
    public MidGenerator(){super();}
    public MidGenerator(int id_game) {
        super(id_game, new MidImprovement());
    }
    @Override
    public float getGeneratorPrice() {
        return (float) (150 * Math.pow(1.15, super.getNGens()));
    }
    @Override
    public float generateCurrency() {
        super.increaseCurrency(super.getNGens());
        return super.getNGens();
    }
    public static String getGeneratorImage() {return NOTES;}
    public float getProductionPerSec() {
        return (float) (getNGens());
    }
}
