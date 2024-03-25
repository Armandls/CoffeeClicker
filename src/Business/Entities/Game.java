package Business.Entities;
/*Class to save the necessary information of the every game the user initializes*/
public class Game {
    private int id_game;
    private int currency_count;

    public Game(int id_game, int currency_count) {
        this.id_game = id_game;
        this.currency_count = currency_count;
    }
    public int getIdGame() {
        return id_game;
    }

    public int getCurrencyCount() {
        return currency_count;
    }
}
