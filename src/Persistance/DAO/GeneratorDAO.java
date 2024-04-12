package Persistance.DAO;

import Business.Entities.Generator.Generator;

import java.util.List;

public interface GeneratorDAO {
    void addGenerator(Generator generator);
    void updateGenerator(Generator generator);
    boolean deleteGenerator(int id_generator);
    List<Generator> getGeneratorsFromGame(int id_game);

}
