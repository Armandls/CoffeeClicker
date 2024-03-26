package Persistance;

import Business.Entities.Generator.Generator;

import java.util.List;

public interface GeneratorDAO {
    void addGenerator(Generator generator);
    Generator getGenerator(int id_generator);
    void updateGenerator(Generator generator);
    boolean deleteGenerator(int id_generator);
    void addGeneratorToGame(int id_game, int id_generator);
    List<Generator> getGeneratorsFromGame(int id_game);

}
