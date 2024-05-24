package Persistance.DAO;

import Business.Entities.Generator.Generator;
import Persistance.Exception.ConnectionErrorException;
import Persistance.Exception.NotFoundException;
import Persistance.Exception.PersistenceException;

import java.util.List;

public interface GeneratorDAO {
    void addGenerator(Generator generator) throws ConnectionErrorException;
    void updateGenerator(Generator generator) throws ConnectionErrorException;
    List<Generator> getGeneratorsFromGame(int id_game) throws PersistenceException;

}