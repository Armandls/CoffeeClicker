package Business.Entities.Generator;
/*Class that implements the abstract class Generator, it represents the most basic type of Generator
@Generator, @MidGenerator, @HighGenerator
*/
public class BasicGenerator extends Generator {
    public BasicGenerator(int id_generator, int n_boosts, int n_currencies) {
        super(id_generator, n_boosts, n_currencies);
    }
}
