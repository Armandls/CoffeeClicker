package Business.Entities.Generator;
/*Class that implements the abstract class Generator, it represents a Generator that is better than the basic one
but not as good as the high
@Generator, @BasicGenerator, @HighGenerator
*/
public class MidGenerator extends Generator {
    public MidGenerator(int id_generator, int n_boosts, int n_currencies, int id_game, int n_gens, String imageUrl) {
        super(id_generator, n_boosts, n_currencies, id_game, n_gens, imageUrl);
    }
}
