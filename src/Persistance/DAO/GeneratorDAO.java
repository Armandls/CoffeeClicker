package Persistance.DAO;

import Business.Entities.Generator.Generator;
import Persistance.Exception.ConnectionErrorException;
import Persistance.Exception.NotFoundException;
import Persistance.Exception.PersistenceException;

import java.util.List;

/**
 * Interface for accessing generator data in the database.
 */
public interface GeneratorDAO {

    /**
     * Adds a new generator to the database.
     * @param generator The generator to be added.
     * @throws ConnectionErrorException If an error occurs while connecting to the database.
     */
    void addGenerator(Generator generator) throws ConnectionErrorException;

    /**
     * Updates an existing generator in the database.
     * @param generator The generator to be updated.
     * @throws ConnectionErrorException If an error occurs while connecting to the database.
     */
    void updateGenerator(Generator generator) throws ConnectionErrorException;

    /**
     * Retrieves a list of generators associated with a game.
     * @param id_game The ID of the game.
     * @return A list of generators associated with the game.
     * @throws PersistenceException If an error occurs during the data retrieval process.
     */
    List<Generator> getGeneratorsFromGame(int id_game) throws PersistenceException;
}
