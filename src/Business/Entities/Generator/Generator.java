package Business.Entities.Generator;

import Business.Entities.Improvement.Improvement;

public abstract class Generator {
    private int id_generator;
    private int n_gens;
    private float n_currencies;
    private int id_game;
    private Improvement improvement;
    private String imageUrl;

    public Generator(int id_generator, float n_currencies, int id_game, int n_gens, Improvement improvement, String imageUrl) {
        this.id_generator = id_generator;
        this.n_currencies = n_currencies;
        this.id_game = id_game;
        this.n_gens = n_gens;
        this.improvement = improvement;
        this.imageUrl = imageUrl;
    }

    public Generator(int id_generator, float n_currencies, int id_game, int n_gens, Improvement improvement, String imageUrl, String description) {
        this.id_generator = id_generator;
        this.n_currencies = n_currencies;
        this.id_game = id_game;
        this.n_gens = n_gens;
        this.improvement = improvement;
        this.imageUrl = imageUrl;
    }

    public Generator(int id_game) {
        this.id_game = id_game;
        this.n_gens = 1;
        this.imageUrl = imageUrl;
        this.n_currencies = 0;
    }

    public Generator(int id_game, Improvement improvement) {
        this.id_game = id_game;
        this.n_gens = 1;
        this.n_currencies = 0;
        this.improvement = improvement;
    }

    public Generator(int id_game, String imageUrl) {
        this.id_game = id_game;
        this.imageUrl = imageUrl;
        this.n_gens = 1;
        this.n_currencies = 0;
        this.improvement = null;
    }

    //Creació de generador buit per obtenir la instancia del generador i saber quin tipus de fill es
    public Generator(){
        this.n_gens = 0;
    }

    public float generateCurrency() {
        return 0;
    }

    public void increaseCurrency(float increaseVal) {
        n_currencies += increaseVal;
    }

    public int getIdGenerator() {
        return id_generator;
    }

    public void setIdGenerator(int id_generator) {
        this.id_generator = id_generator;
    }

    public void setIdImprovement(int id) {
        this.improvement.setIdImprovement(id);
    }

    public int getIdGame() {
        return id_game;
    }

    public int getNGens() {
        return n_gens;
    }


    public Improvement getImprovement() {
        return improvement;
    }

    public void setImprovement(Improvement improvement) {
        this.improvement = improvement;
    }

    public float getNCurrencies() {
        return n_currencies;
    }

    public float getGeneratorPrice() {return 0;}

    public void addGenerator() {
        n_gens++;
    }

    public String getGeneratorImage(){return null;}

    public void incrementImprovementLevel() {
        improvement.incrementImprovementLevel();
    }

    public float getProductionPerSec() {
        return 0;
    }
    public float getProductionPercentage(float totalCurrency) {
        return (getProductionPerSec()/totalCurrency) * 100;
    }
}
