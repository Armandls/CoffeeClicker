package Business.Entities;

import Business.Entities.Generator.BasicGenerator;
import Business.Entities.Generator.Generator;
import Business.Entities.Generator.HighGenerator;
import Business.Entities.Generator.MidGenerator;
import Business.Exception.GeneratorException.GeneratorAddedException;

import java.util.ArrayList;

/*Class to save the necessary information of the every game the user initializes*/
public class Game {
    private int id_game;
    private int currency_count;
    private boolean finished;
    private String user;
    private ArrayList<Generator> gameGenerators;

    public Game(int id_game, int currency_count, boolean finished, String mail_user) {
        this.id_game = id_game;
        this.currency_count = currency_count;
        this.finished = finished;
        this.user = user;
        this.gameGenerators = new ArrayList<>();
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
    public void addGeneratorToGame(String type, String imageUrl) throws GeneratorAddedException{
        Generator toAdd;
        for (Generator auxGen : gameGenerators) {
            if (auxGen.getClass().getSimpleName().contains(type)) {
                auxGen.addGenerator();
                return;
            }
        }
        switch (type) {
            case "Basic":
                gameGenerators.add(new BasicGenerator(id_game, imageUrl));
                throw new GeneratorAddedException("Generator added");
            case "Mid":
                gameGenerators.add(new MidGenerator(id_game, imageUrl));
                throw new GeneratorAddedException("Generator added");
            case "High":
                gameGenerators.add(new HighGenerator(id_game, imageUrl));
                throw new GeneratorAddedException("Generator added");
        }
    }

}
