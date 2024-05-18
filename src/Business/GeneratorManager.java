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

/*Class to manage the different entities regarding the Generators
 *  @Generator package
 */
public class GeneratorManager {
    GeneratorDAO generatorDAO;
    GeneratorStore generatorStore;

    String[] genTypes = {"Basic", "Mid", "High"};
    String[] generatorNames = {"REDBULL", "NOTES", "CEUS"};

    public GeneratorManager(GeneratorDAO generatorDAO) throws BusinessException{
        this.generatorDAO = generatorDAO;
        //this.gameManager = gameManager;
        //this.generatorStore = new GeneratorStore(getGenerators());  //Aqui faltaria passar-li la partida.
    }
    public GeneratorManager(){
     this.generatorDAO = new SQLGenerator();
    }

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

    public void updateGenerator(Generator generator) throws ConnectionErrorException {
        generatorDAO.updateGenerator(generator);
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

    public int getGeneratorIdFromGame(String type, int gameId){
        Generator auxGen = null;
        int outputVal = -1;
        try {
            auxGen = getGeneratorFromGame(gameId, type);
            outputVal = auxGen.getIdGenerator();
        } catch (BusinessException e) {
        }
        return outputVal;
    }

    public int purchaseNewGenerator(String type, int gameId) throws BusinessException{
        int generatorId = -1;
        try {
            Generator auxGen = getGeneratorFromGame(gameId, type);
            auxGen.addGenerator();
            generatorId = auxGen.getIdGenerator();
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

    public String[] getShopNames() {
        return generatorNames.clone();
    }

    public int[] getNumGeneratorsInShop(int gameId) throws BusinessException {
        int[] outputArr = new int[genTypes.length];
        for(int i = 0; i < genTypes.length; i++) {
            try {
                Generator auxGen = getGeneratorFromGame(gameId, genTypes[i]);
                outputArr[i] = auxGen.getNGens();
            } catch (NoGeneratorException e) {
                outputArr[i] = 0;
            } catch (BusinessException e) {
                throw new BusinessException(e.getMessage());
            }
        }
        return outputArr;
    }


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


    public String[] getShopImages() {
        String[] shopImages = new String[genTypes.length];
        String imageStr = "";
        for(int i = 0; i < genTypes.length; i++) {
            switch (i) {
                case 0:
                    imageStr = BasicGenerator.getGeneratorImage();
                    break;
                case 1:
                    imageStr = MidGenerator.getGeneratorImage();
                    break;
                case 2:
                    imageStr = HighGenerator.getGeneratorImage();
                    break;
            }
            shopImages[i] = imageStr;
        }
        return shopImages;
    }

    public float[] getAllProductionPerSec(int gameId) throws BusinessException {
        float[] productionPerSec = new float[3];
        int i = 0;
        for (Generator gen : getGenerators(gameId)) {
            productionPerSec[i] = gen.getProductionPerSec();
        }
        return productionPerSec;
    }


    public float[] getAllProductionPercentage(int gameId, float totalCurrency) throws BusinessException {
        float[] productionPercentage = new float[3];
        int i = 0;
        for (Generator gen : getGenerators(gameId)) {
            productionPercentage[i] = gen.getProductionPercentage(totalCurrency);
        }
        return productionPercentage;
    }
}
