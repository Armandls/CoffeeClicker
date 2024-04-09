package Business.Entities.Generator;
/*Class that implements the abstract class Generator, it represents the best type of Generator
@Generator, @BasicGenerator, @MidGenerator
*/
public class HighGenerator extends Generator{
    public HighGenerator(int id_generator, int n_boosts, int n_currencies, int id_game, int n_gens, String imageUrl) {
        super(id_generator, n_boosts, n_currencies, id_game, n_gens, imageUrl);
    }
}
