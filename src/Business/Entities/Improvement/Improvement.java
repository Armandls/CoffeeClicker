package Business.Entities.Improvement;
/*Abstract class to represent the shared attributes of the improvements available to the Generators*/
public abstract class Improvement {
    private int id_improvement;
    private int level;
    private String imageUrl;

    public Improvement(int id_improvement, int level, String imageUrl) {
        this.id_improvement = id_improvement;
        this.level = level;
        this.imageUrl = imageUrl;
    }

    public Improvement(int level, String imageUrl) {
        this.level = level;
        this.imageUrl = imageUrl;
    }


    public int getIdImprovement() {
        return id_improvement;
    }

    public void setIdImprovement(int id_improvement) {
        this.id_improvement = id_improvement;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void incrementImprovementLevel() {
        level++;
    }

    public double getMultiplier() {
        return 0;
    }
}
