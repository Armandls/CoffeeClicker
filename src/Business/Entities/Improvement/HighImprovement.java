package Business.Entities.Improvement;
/*Class that implements the abstract class Improvement, it represents the Improvement for the HighGenerator
@Improvement, @HighGenerator
*/
public class HighImprovement extends Improvement{
    private final double multiplier = 1.7;
    public HighImprovement(int idImprovement, int level, String imageUrl) {
        super(idImprovement, level, imageUrl);
    }
    public HighImprovement() {super(0, "path_basic_image");}
    public double getMultiplier() {
        return multiplier;
    }
}
