package Business.Entities;

import Business.Entities.Generator.BasicGenerator;
import Business.Entities.Generator.Generator;
import Business.Entities.Generator.HighGenerator;
import Business.Entities.Generator.MidGenerator;
import Business.Exception.GeneratorException.NotEnoughCurrencyException;

import java.util.ArrayList;
import java.util.List;

/*Class to represent store where the user will be able to buy the different kinds of Generators of currency*/
public class GeneratorStore {
    Game game;
    final List<Generator> generators;
    public GeneratorStore(List<Generator> generators) {
        this.generators = new ArrayList<>(generators);
    }
    public GeneratorStore() {
        this.generators = new ArrayList<>();
    }
    public List<Generator> getStoreGenerators() {
        return generators;
    }

    public void buyGenerator(String type) throws NotEnoughCurrencyException{
        Generator gen;
        switch (type) {
            case "Basic":
                gen = new BasicGenerator();
                break;
            case "Mid":
                gen = new MidGenerator();
                break;
            case "High":
                gen = new HighGenerator();
                break;
            default:
                gen = new BasicGenerator();
                break;
        }
        for (Generator generator : generators) {
            if (generator.getClass() == gen.getClass()) {
                if (game.getCurrencyCount() > generator.getGeneratorPrice()){
                    game.substractCurrency(generator.getGeneratorPrice());
                    generator.addGenerator();
                }
                else {
                    throw new NotEnoughCurrencyException("Not enough currency to buy this generator");
                }
            }
        }
    }

}