package Business.Entities.Generator;
/*Abstract class to represent the shared attributes of a Generator*/
public abstract class Generator {
    private int id_generator;
    private int n_boosts;
    private int n_currencies;

    // Constructor
    public Generator(int id_generator, int n_boosts, int n_currencies) {
        this.id_generator = id_generator;
        this.n_boosts = n_boosts;
        this.n_currencies = n_currencies;
    }

    public int getIdGenerator() {
        return id_generator;
    }

    public void setIdGenerator(int id_generator) {
        this.id_generator = id_generator;
    }


    public int getNBoosts() {
        return n_boosts;
    }

    public void setNBoosts(int n_boosts) {
        this.n_boosts = n_boosts;
    }

    public int getNCurrencies() {
        return n_currencies;
    }

    public void setNCurrencies(int n_currencies) {
        this.n_currencies = n_currencies;
    }

}
