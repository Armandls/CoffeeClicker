package Business;

import Business.Entities.Generator.BasicGenerator;
import Business.Entities.Generator.Generator;
import Business.Entities.Generator.HighGenerator;
import Business.Entities.Generator.MidGenerator;
import Business.Entities.GeneratorStore;
import Business.Exception.BusinessException;
import Business.Exception.GeneratorException.NoGeneratorException;
import Persistance.DAO.GeneratorDAO;
import Persistance.Exception.ConnectionErrorException;
import Persistance.Exception.NotFoundException;
import Persistance.Exception.PersistenceException;
import Persistance.SQL.SQLGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to manage the different entities regarding the Generators.
 */
public class GeneratorManager {

    /**
     * The data access object for managing generators.
     */
    final GeneratorDAO generatorDAO;

    /**
     * Array containing the types of generators.
     */
    final String[] genTypes = {"Basic", "Mid", "High"};

    //
    public GeneratorManager(GeneratorDAO generatorDAO) {
        this.generatorDAO = generatorDAO;
    }
    public GeneratorManager(){
        this.generatorDAO = new SQLGenerator();
    }

    /**
     * Retrieves the generators associated with the specified game.
     * @param gameId The ID of the game.
     * @return The list of generators.
     * @throws BusinessException If an error occurs during the retrieval process.
     */
    public List<Generator> getGenerators(int gameId) throws BusinessException {
        List<Generator> generators;
        try {
            generators = generatorDAO.getGeneratorsFromGame(gameId);
        } catch (NotFoundException e) {
            throw new NoGeneratorException("No generators were found for game with id -> " + gameId); //gameManager.getIdGame());
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
        return generators;
    }

    /**
     * Updates the specified generator.
     * @param generator The generator to be updated.
     * @throws ConnectionErrorException If there is an error connecting to the data source.
     */
    public void updateGenerator(Generator generator) throws ConnectionErrorException {
        generatorDAO.updateGenerator(generator);
    }

    /**
     * Retrieves the types of generators associated with the specified game.
     * @param gameId The ID of the game.
     * @return The list of generator types.
     * @throws BusinessException If an error occurs during the retrieval process.
     */
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
     * Determines if a generator purchase is available based on the provided currency, game ID, and generator type.
     * @param currency The available currency.
     * @param gameId The ID of the game.
     * @param type The type of generator.
     * @return True if the purchase is available, otherwise false.
     * @throws BusinessException If an error occurs during the process.
     */
    public boolean generatorPurchaseAvailable(float currency, int gameId, String type) throws BusinessException{
        try {
            Generator auxGen = getGeneratorFromGame(gameId, type);
            return (currency >= auxGen.getGeneratorPrice());
        }
        catch (NoGeneratorException e) {
            switch (type) {
                case "Basic":
                    return (currency >= new BasicGenerator().getGeneratorPrice());
                case "Mid":
                    return (currency >= new MidGenerator().getGeneratorPrice());
                case "High":
                    return (currency >= new HighGenerator().getGeneratorPrice());
            }
        }
        return false;
    }

    /**
     * Retrieves the ID of the specified generator from the game.
     * @param type The type of generator.
     * @param gameId The ID of the game.
     * @return An array containing the generator ID and improvement ID.
     */
    public int[] getGeneratorIdFromGame(String type, int gameId){
        Generator auxGen = null;
        int[] outputVal = new int[2];
        try {
            auxGen = getGeneratorFromGame(gameId, type);
            outputVal[0] = auxGen.getIdGenerator();
            outputVal[1] = auxGen.getImprovement().getIdImprovement();

        } catch (BusinessException e) {
        }
        return outputVal;
    }

    /**
     * Purchases a new generator of the specified type for the specified game.
     * @param type The type of generator.
     * @param gameId The ID of the game.
     * @return An array containing the generator ID and improvement ID.
     * @throws BusinessException If an error occurs during the process.
     */
    public int[] purchaseNewGenerator(String type, int gameId) throws BusinessException{
        int[] generatorId = new int[2];
        generatorId[0] = -1;
        generatorId[1] = -1;
        try {
            Generator auxGen = getGeneratorFromGame(gameId, type);
            auxGen.addGenerator();
            generatorId[0] = auxGen.getIdGenerator();
            generatorDAO.updateGenerator(auxGen);
        }
        catch (NoGeneratorException e) {
            try {
                switch (type) {
                    case "Basic":
                        generatorDAO.addGenerator(new BasicGenerator(gameId));
                        break;
                    case "Mid":
                        generatorDAO.addGenerator(new MidGenerator(gameId));
                        break;
                    case "High":
                        generatorDAO.addGenerator(new HighGenerator(gameId));
                        break;
                }
                generatorId = getGeneratorIdFromGame(type, gameId);
            } catch (ConnectionErrorException ex) {
                throw new BusinessException(e.getMessage());
            }
        } catch (ConnectionErrorException e) {
            throw new BusinessException(e.getMessage());
        }
        return generatorId;
    }

    /**
     * Retrieves an empty generator of the specified type.
     * @param type The type of generator.
     * @return An empty generator.
     */
    public Generator getEmptyGenerator(String type) {
        switch (type) {
            case "Basic":
                return new BasicGenerator();
            case "Mid":
                return new MidGenerator();
            case "High":
                return new HighGenerator();
        }
        return null;
    }

    /**
     * Retrieves the prices of generators available in the shop for the specified game.
     * @param gameId The ID of the game.
     * @return An array containing the prices of the generators.
     * @throws BusinessException If an error occurs during the process.
     */
    public int[] getShopPrices(int gameId) throws BusinessException{
        int[] outputArr = new int[genTypes.length];
        for(int i = 0; i < genTypes.length; i++) {
            try {
                Generator auxGen = getGeneratorFromGame(gameId, genTypes[i]);
                outputArr[i] = (int) Math.round(auxGen.getGeneratorPrice());
            } catch (NoGeneratorException e) {
                outputArr[i] = (int) Math.round(getEmptyGenerator(genTypes[i]).getGeneratorPrice());
            } catch (BusinessException e) {
                throw new BusinessException(e.getMessage());
            }
        }
        return outputArr;
    }


    /**
     * Retrieves a generator of the specified type from the game.
     * @param gameId The ID of the game.
     * @param type The type of generator.
     * @return The generator.
     * @throws BusinessException If an error occurs during the process.
     */
    public Generator getGeneratorFromGame(int gameId, String type) throws BusinessException{
        try {
            List<Generator> gens = getGenerators(gameId);
            for (Generator aux : gens) {
                if (aux.getClass().getSimpleName().contains(type)) {
                    return aux;
                }
            }
        } catch (NoGeneratorException e) {
            throw new NoGeneratorException("No generators were found for game with id -> " + gameId);
        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage());
        }
        throw new NoGeneratorException("No gens");
    }

    /**
     * Retrieves the number of instances of the specified generator type given a game ID.
     * @param gameId The ID of the game.
     * @param type The generator type.
     * @return The number of generators.
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
     * Retrieves the number of instances of all generator types given a game ID.
     * @param gameId The ID of the game.
     * @return An array containing the number of generators for each type.
     */
    public int[] getAllNumberOfGenerators(int gameId) {
        List<Generator> generators;
        int[] n_gens = new int[3];
        int i =0;
        try {
            generators = generatorDAO.getGeneratorsFromGame(gameId);
        } catch (PersistenceException e) {
            throw new RuntimeException(e); // Mètode es crida sobre generators previament consultats de la bbdd, per tant excepció mai es llançarà
        }
        for(Generator g: generators) {
            n_gens[i] = g.getNGens();
            i++;
        }
        return n_gens;
    }

    /**
     * Retrieves the boost level of a specified generator in a given game.
     * @param gameId The ID of the game.
     * @param type The generator type.
     * @return The boost level.
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

    /**
     * Retrieves the production per second of all generators in a given game.
     * @param gameId The ID of the game.
     * @return An array containing the production per second of each generator type.
     * @throws BusinessException If an error occurs during the process.
     */
    public float[] getAllProductionPerSec(int gameId) throws BusinessException {
        float[] productionPerSec = new float[3];
        int i = 0;
        for (Generator gen : getGenerators(gameId)) {
            productionPerSec[i] = gen.getProductionPerSec();
            i++;
        }
        return productionPerSec;
    }

    /**
     * Retrieves the total production per second of all generators in a given game.
     * @param gameId The ID of the game.
     * @return The total production per second.
     * @throws BusinessException If an error occurs during the process.
     */
    public float getTotalProductionPerSec(int gameId) throws BusinessException{
        float totalCurrency = 0;
        for (Generator aux : getGenerators(gameId)) {
            totalCurrency += aux.getProductionPerSec();
        }
        return totalCurrency;
    }

    /**
     * Retrieves the production percentage of all generators in a given game.
     * @param gameId The ID of the game.
     * @return An array containing the production percentage of each generator type.
     * @throws BusinessException If an error occurs during the process.
     */
    public float[] getAllProductionPercentage(int gameId) throws BusinessException {
        float totalCurrency = getTotalProductionPerSec(gameId);

        float[] productionPercentage = new float[3];
        int i = 0;
        for (Generator gen : getGenerators(gameId)) {
            productionPercentage[i] = gen.getProductionPercentage(totalCurrency);
            i++;
        }
        return productionPercentage;
    }

    /**
     * Retrieves the improvement levels of all generators in a given game.
     * @param gameId The ID of the game.
     * @return An array containing the improvement levels of each generator type.
     */
    public int[] getImprovementLevels(int gameId) {
        List<Generator> generators = new ArrayList<>();
        int[] gen_lvl = new int[3];
        int i = 0;
        try {
            generators = generatorDAO.getGeneratorsFromGame(gameId);
        } catch (PersistenceException e) {
            throw new RuntimeException(e); // Mètode es crida sobre generators previament consultats de la bbdd, per tant excepció mai es llançarà
        }
        for (Generator g : generators) {
            gen_lvl[i] = g.getImprovement().getLevel();
            i++;
        }
        return gen_lvl;
    }
}
