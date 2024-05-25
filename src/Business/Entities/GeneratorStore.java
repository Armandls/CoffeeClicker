package Business.Entities;

import Business.Entities.Generator.BasicGenerator;
import Business.Entities.Generator.Generator;
import Business.Entities.Generator.HighGenerator;
import Business.Entities.Generator.MidGenerator;
import Business.Exception.GeneratorException.NotEnoughCurrencyException;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase para representar una tienda donde el usuario podrá comprar los diferentes tipos de generadores de moneda.
 */
public class GeneratorStore {
    Game game; // Juego al que pertenece la tienda
    final List<Generator> generators; // Lista de generadores disponibles en la tienda

    /**
     * Constructor de la clase GeneratorStore.
     * @param generators Lista de generadores disponibles en la tienda.
     */
    public GeneratorStore(List<Generator> generators) {
        this.generators = new ArrayList<>(generators);
    }

    /**
     * Constructor vacío de la clase GeneratorStore.
     * Inicializa la lista de generadores.
     */
    public GeneratorStore() {
        this.generators = new ArrayList<>();
    }

    /**
     * Obtiene la lista de generadores disponibles en la tienda.
     * @return Lista de generadores disponibles en la tienda.
     */
    public List<Generator> getStoreGenerators() {
        return generators;
    }

    /**
     * Permite al usuario comprar un generador de un tipo específico.
     * @param type Tipo de generador a comprar.
     * @throws NotEnoughCurrencyException Si el usuario no tiene suficiente moneda para comprar el generador.
     */
    public void buyGenerator(String type) throws NotEnoughCurrencyException {
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
                if (game.getCurrencyCount() > generator.getGeneratorPrice()) {
                    game.substractCurrency(generator.getGeneratorPrice());
                    generator.addGenerator();
                } else {
                    throw new NotEnoughCurrencyException("Not enough currency to buy this generator");
                }
            }
        }
    }
}
