package Business.Entities.Improvement;
/*Class that implements the abstract class Improvement, it represents the Improvement for the MidGenerator
@Improvement, @MidGenerator
*/
public class MidImprovement extends Improvement{
    private final double multiplier = 1.5;
    public MidImprovement(int idImprovement, int level, String imageUrl) {
        super(idImprovement, level, imageUrl);
    }
    public MidImprovement() {super(0, "path_basic_image");}

    public double getMultiplier() {
        return multiplier;
    }
}
