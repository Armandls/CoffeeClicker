package Business.Entities.Generator;

import Business.Entities.Improvement.BasicImprovement;
import Business.Entities.Improvement.Improvement;

/*Class that implements the abstract class Generator, it represents the most basic type of Generator
@Generator, @MidGenerator, @HighGenerator
*/
public class BasicGenerator extends Generator {
    private static final String REDBULL = "/files/Resources/Images/redBull.png";
    public BasicGenerator(int id_generator, float n_currencies, int id_game, int n_gens,  Improvement improvement, String imageUrl) {
        super(id_generator, n_currencies, id_game, n_gens, improvement, imageUrl);
    }
    //Creació de generador buit per obtenir la instancia del generador i saber quin tipus de fill es
    public BasicGenerator(){super();}
    public BasicGenerator(int id_game, String imageUrl) {
        super(id_game, imageUrl);
    }

    public BasicGenerator(int id_game) {
        super(id_game);
    }
    @Override
    public float getGeneratorPrice() {
        return (float) (10 * Math.pow(1.07, super.getNGens()));
    }
    @Override
    public float generateCurrency() {
        super.increaseCurrency((float) 0.2 * super.getNGens());
        return (float) 0.2 * super.getNGens();
    }
    public static String getGeneratorImage(){return REDBULL;}
}
