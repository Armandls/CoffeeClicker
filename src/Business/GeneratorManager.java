package Business;

import Business.Entities.Game;
import Business.Entities.Generator.Generator;
import Business.Entities.GeneratorStore;
import Business.Exception.BusinessException;
import Business.Exception.GeneratorException.GeneratorException;
import Business.Exception.GeneratorException.NoGeneratorException;
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

    public GeneratorManager(GameManager gameManager) throws BusinessException{
        generatorDAO = new SQLGenerator();
        this.gameManager = gameManager;
        this.generatorStore = new GeneratorStore(getGenerators());
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






}
