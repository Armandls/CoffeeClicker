package Business.Entities;
/*Class to save the necessary information of the every game the user initializes*/
public class Game {
    private int id_game;
    private int currency_count;
    private boolean finished;
    private String user;

    public Game(int id_game, int currency_count, boolean finished, String mail_user) {
        this.id_game = id_game;
        this.currency_count = currency_count;
        this.finished = finished;
        this.user = user;
    }
    public int getIdGame() {
        return id_game;
    }

    public int getCurrencyCount() {
        return currency_count;
    }
    public void substractCurrency(int amount) {
        currency_count -= amount;
    }
    public String getUser() {
        return user;
    }
    public boolean isFinished() {
        return finished;
    }
}
