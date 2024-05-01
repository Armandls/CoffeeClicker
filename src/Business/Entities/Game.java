package Business.Entities;
/*Class to save the necessary information of the every game the user initializes*/
public class Game {
    private int id_game;
    private int currency_count;
    private boolean finished;

    public Game(int id_game, int currency_count, boolean finished) {
        this.id_game = id_game;
        this.currency_count = currency_count;
        this.finished = finished;
    }
    public int getIdGame() {
        return id_game;
    }

    public int getCurrencyCount() {
        return currency_count;
    }
    public boolean isFinished() {
        return finished;
    }
}
