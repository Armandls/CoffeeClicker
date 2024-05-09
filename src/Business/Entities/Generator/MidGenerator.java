package Business.Entities.Generator;

import Business.Entities.Improvement.Improvement;

/*Class that implements the abstract class Generator, it represents a Generator that is better than the basic one
but not as good as the high
@Generator, @BasicGenerator, @HighGenerator
*/
public class MidGenerator extends Generator {
    private final String CEUS = "/files/Resources/Images/ceus.png";
    public MidGenerator(int id_generator, int n_currencies, int id_game, int n_gens, Improvement improvement, String imageUrl) {
        super(id_generator, n_currencies, id_game, n_gens, improvement, imageUrl);
    }
    //Creació de generador buit per obtenir la instancia del generador i saber quin tipus de fill es
    public MidGenerator(){super();}
    public MidGenerator(int id_game) {
        super(id_game);
    }
    @Override
    public int getGeneratorPrice() {
        return (int) Math.round(150 * Math.pow(1.15, super.getNGens()));
    }
    public float generateCurrency() {
        super.increaseCurrency(super.getNGens());
        return super.getNGens();
    }
    public String getGeneratorImage() {return CEUS;}
}
