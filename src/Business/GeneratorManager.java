package Business;

import Business.Entities.Game;
import Business.Entities.Generator.BasicGenerator;
import Business.Entities.Generator.Generator;
import Business.Entities.Generator.HighGenerator;
import Business.Entities.Generator.MidGenerator;
import Business.Entities.GeneratorStore;
import Business.Exception.BusinessException;
import Business.Exception.GeneratorException.GeneratorException;
import Business.Exception.GeneratorException.NoGeneratorException;
import Business.Exception.GeneratorException.NotEnoughCurrencyException;
import Persistance.DAO.GeneratorDAO;
import Persistance.Exception.NotFoundException;
import Persistance.Exception.PersistenceException;
import Persistance.SQL.SQLGameGenDAO;
import Persistance.SQL.SQLGenerator;

import java.util.ArrayList;
import java.util.List;

/*Class to manage the different entities regarding the Generators
 *  @Generator package
 */
public class GeneratorManager {
    GeneratorDAO generatorDAO;
    GameManager gameManager;
    GeneratorStore generatorStore;

    public GeneratorManager(GameManager gameManager, GeneratorDAO generatorDAO) throws BusinessException{
        this.generatorDAO = generatorDAO;
        this.gameManager = gameManager;
        //this.generatorStore = new GeneratorStore(getGenerators());  //Aqui faltaria passar-li la partida.
    }
    public GeneratorManager(){};

    public List<Generator> getGenerators() throws BusinessException {
        List<Generator> generators = new ArrayList<>();
        try {
            generators = generatorDAO.getGeneratorsFromGame(0);//gameManager.getIdGame());
        } catch (NotFoundException e) {
            throw new NoGeneratorException("No generators were found for game with id -> " + 0); //gameManager.getIdGame());
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
        return generators;
    }

    public List<String> getGeneratorsTypes(int gameId) throws BusinessException {
        List<Generator> generators = new ArrayList<>();
        List<String> generator_types = new ArrayList<>();
        try {
            generators = generatorDAO.getGeneratorsFromGame(gameId);
        } catch (NotFoundException e) {
            throw new NoGeneratorException("No generators were found for game with id -> " + gameId);
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
        for(Generator g: generators) {
            if(g instanceof BasicGenerator) generator_types.add("Basic");
            else if(g instanceof MidGenerator) generator_types.add("Mid");
            else if (g instanceof HighGenerator) generator_types.add("High");
        }
        return generator_types;
    }

    /**
     * Returns number of instances of specified generator type given a game id
     * @param type, gameId
     * @return number of generators
     */
    public int getNumberOfGenerators(int gameId, String type) {
        List<Generator> generators = new ArrayList<>();
        int n_gens = 0;
        try {
            generators = generatorDAO.getGeneratorsFromGame(gameId);
        } catch (PersistenceException e) {
            throw new RuntimeException(e); // Mètode es crida sobre generators previament consultats de la bbdd, per tant excepció mai es llançarà
        }
        for(Generator g: generators) {
            if (g.getClass().getSimpleName().contains(type)) { //simpleName: BasicGenerator, MidGenerator, HighGenerator
                 n_gens = g.getNGens();
            }
        }
        return n_gens;
    }

    /**
     * Return boost level of a specified generator in a given game
     * @param gameId game identification
     * @param type generator's type
     * @return boost level
     */
    public int getLevelOfGenerator(int gameId, String type) {
        List<Generator> generators = new ArrayList<>();
        int gen_lvl = 0;
        try {
            generators = generatorDAO.getGeneratorsFromGame(gameId);
        } catch (PersistenceException e) {
            throw new RuntimeException(e); // Mètode es crida sobre generators previament consultats de la bbdd, per tant excepció mai es llançarà
        }
        for(Generator g: generators) {
            if (g.getClass().getSimpleName().contains(type)) { //simpleName: BasicGenerator, MidGenerator, HighGenerator
                gen_lvl= g.getImprovement().getLevel();
            }
        }
        return gen_lvl;
    }
    public void buyGenerator(String type) throws NotEnoughCurrencyException {
        generatorStore.buyGenerator(type);
    }

    public List<Generator> getStoreGenerators() {
        return generatorStore.getStoreGenerators();
    }






}
