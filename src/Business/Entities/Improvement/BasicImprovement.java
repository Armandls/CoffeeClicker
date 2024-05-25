package Business.Entities.Improvement;
/*Class that implements the abstract class Improvement, it represents the Improvement for the BasicGenerator
@Improvement, @BasicGenerator
*/
public class BasicImprovement extends Improvement {

    private final double multiplier = 1.2;
    public BasicImprovement(int idImprovement, int level, String imageUrl) {
        super(idImprovement, level, imageUrl);
    }
    public BasicImprovement() {super(0, "path_basic_image");}

    public double getMultiplier() {
        return multiplier;
    }
}
