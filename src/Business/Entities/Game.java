package Business.Entities;

import Business.Entities.Generator.BasicGenerator;
import Business.Entities.Generator.Generator;
import Business.Entities.Generator.HighGenerator;
import Business.Entities.Generator.MidGenerator;
import Business.Exception.BusinessException;
import Business.Exception.GeneratorException.GeneratorAddedException;

import java.util.ArrayList;

/*Class to save the necessary information of the every game the user initializes*/
public class Game {
    private int id_game;
    private float currency_count;
    private boolean finished;
    private String mail_user;
    private ArrayList<Generator> gameGenerators;

    public Game() {
        this.gameGenerators = new ArrayList<>(); // necessari per evitar nullPOinter exception en inicialitzar generators
    }

    public Game(int id_game, float currency_count, boolean finished, String mail_user) {
        this.id_game = id_game;
        this.currency_count = currency_count;
        this.finished = finished;
        this.mail_user = mail_user;
        this.gameGenerators = new ArrayList<>();
    }
    public void setId_game(int id_game) {
        this.id_game = id_game;
    }
    public int getIdGame() {
        return id_game;
    }
    public float getCurrencyCount() {
        return currency_count;
    }
    public void substractCurrency(float amount) {
        currency_count -= amount;
    }
    public String getUser() {
        return mail_user;
    }
    public boolean isFinished() {
        return finished;
    }
    public void addGeneratorToGame(String type, int idGenerator) {
        Generator toAdd;
        for (Generator auxGen : gameGenerators) {
            if (auxGen.getClass().getSimpleName().contains(type)) {
                currency_count -= auxGen.getGeneratorPrice();
                auxGen.addGenerator();
                return;
            }
        }
        switch (type) {
            case "Basic":
                toAdd = new BasicGenerator(id_game);
                break;
            case "Mid":
                toAdd = new MidGenerator(id_game);
                break;
            case "High":
                toAdd = new HighGenerator(id_game);
                break;
            default:
                toAdd = new BasicGenerator(id_game);
                break;
        }
        toAdd.setIdGenerator(idGenerator);
        currency_count -= toAdd.getGeneratorPrice();
        gameGenerators.add(toAdd);
    }
    public void  initGenerator(Generator generator) {
        gameGenerators.add(generator);
    }

    public boolean getFinished() {
        return finished;
    }

    public void increaseCurrency() {
        currency_count += 0.5F;
        //currency_count++;
    }

    public void generatorsProduction() {
        int producedAmount = 0;
        for (Generator aux : gameGenerators) {
            //producedAmount += aux.get
        }
    }
}
