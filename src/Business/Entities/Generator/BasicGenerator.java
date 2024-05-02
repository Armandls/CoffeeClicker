package Business.Entities.Generator;

import Business.Entities.Improvement.Improvement;

/*Class that implements the abstract class Generator, it represents the most basic type of Generator
@Generator, @MidGenerator, @HighGenerator
*/
public class BasicGenerator extends Generator {
    public BasicGenerator(int id_generator, int n_currencies, int id_game, int n_gens,  Improvement improvement, String imageUrl) {
        super(id_generator, n_currencies, id_game, n_gens, improvement, imageUrl);
    }
    //Creació de generador buit per obtenir la instancia del generador i saber quin tipus de fill es
    public BasicGenerator(){super();}
    @Override
    public int getGeneratorPrice() {
        return (int) Math.round(10 * Math.pow(1.07, super.getNGens()));
    }
    public float generateCurrency() {
        super.increaseCurrency((float) 0.2 * super.getNGens());
        return (float) 0.2 * super.getNGens();
    }
}
