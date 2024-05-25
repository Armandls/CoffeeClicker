package Persistance.SQL;

import Business.Entities.Generator.BasicGenerator;
import Business.Entities.Generator.Generator;
import Business.Entities.Generator.HighGenerator;
import Business.Entities.Generator.MidGenerator;
import Business.Entities.Improvement.BasicImprovement;
import Business.Entities.Improvement.HighImprovement;
import Business.Entities.Improvement.Improvement;
import Business.Entities.Improvement.MidImprovement;
import Persistance.DAO.GeneratorDAO;
import Persistance.Exception.ConnectionErrorException;
import Persistance.Exception.PersistenceException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the GeneratorDAO interface for SQL databases.
 */
public class SQLGenerator implements GeneratorDAO {

    /**
     * Adds a new generator to the database.
     *
     * @param generator The generator to add.
     * @throws ConnectionErrorException If there is an error connecting to the database.
     */
    @Override
    public void addGenerator(Generator generator) throws ConnectionErrorException {
        int idAddedImprovement = 0;
        //1- Inserim a la taula "Improvement" la millora associada al generador passat per paràmetres
        String improvementQuery = "INSERT INTO improvements(improvement_level, photo, improvement_type) VALUES ('" +
        //String improvementQuery = "INSERT INTO improvements() VALUES ('" +
                generator.getImprovement().getLevel() + "', '" +
                generator.getImprovement().getImageUrl() + "', ";

        if (generator.getImprovement() instanceof BasicImprovement) {
            improvementQuery += "'Basic'";
        } else if (generator.getImprovement() instanceof MidImprovement) {
            improvementQuery += "'Mid'";
        } else if (generator.getImprovement() instanceof HighImprovement) {
            improvementQuery += "'High'";
        }
        improvementQuery += ");";
        idAddedImprovement = SQLConnector.getInstance().insertQueryAutogeneratedKey(improvementQuery);

        //2- Una vegada tenim la referència de millora inserida a la base de dades podem afegir el generador
        //String generatorQuery = "INSERT INTO generator(id_generator, n_currencies, id_game, n_gens, photo, type, improvement) VALUES ('" +
        String generatorQuery = "INSERT INTO generator(n_currencies, id_game, n_gens, photo, type, improvement) VALUES ('" +

                //generator.getIdGenerator() + "', '" +
                generator.getNCurrencies() + "', '" +
                generator.getIdGame() + "', '" +
                generator.getNGens() + "', '" +
                generator.getGeneratorImage() + "', ";
        switch (generator) {
            case BasicGenerator basicGenerator -> generatorQuery += "'Basic'";
            case MidGenerator midGenerator -> generatorQuery += "'Mid'";
            case HighGenerator highGenerator -> generatorQuery += "'High'";
            case null, default -> {
            }
        }
        generatorQuery += ", '" +
        idAddedImprovement +
        //generator.getImprovement().getIdImprovement() +
        "');"; // Include improvement


        SQLConnector.getInstance().insertQuery(generatorQuery);
    }

    /**
     * Updates an existing generator in the database.
     *
     * @param generator The generator to update.
     * @throws ConnectionErrorException If there is an error connecting to the database.
     */
    @Override
    public void updateGenerator(Generator generator) throws ConnectionErrorException {
        // Actualitzem valors millora
        String improvementQuery = "UPDATE improvements SET " +
                "improvement_level = '" + generator.getImprovement().getLevel() + "', " +
                "photo = '" + generator.getImprovement().getImageUrl() + "' " +
                "WHERE id_improvement = '" + generator.getImprovement().getIdImprovement() + "';";
        try {
            SQLConnector.getInstance().updateQuery(improvementQuery);

        } catch (ConnectionErrorException e) {
            throw new ConnectionErrorException("Error updating generator with id <" + generator.getIdGenerator() + ">. " + e.getMessage());
        }

        // Actualitzem valors generator
        String generatorQuery = "UPDATE generator SET " +
                "n_currencies = '" + generator.getNCurrencies() + "', " +
                "id_game = '" + generator.getIdGame() + "', " +
                "photo = '" + generator.getGeneratorImage() + "', " +
                "n_gens = '" + generator.getNGens() + "', " +
                "improvement = '" + generator.getImprovement().getIdImprovement() + "' " + // Include improvement
                "WHERE id_generator = '" + generator.getIdGenerator() + "';";
        try {
            SQLConnector.getInstance().updateQuery(generatorQuery);
        } catch (ConnectionErrorException e) {
            throw new ConnectionErrorException("Error updating generator with id <" + generator.getIdGenerator() + ">. " + e.getMessage());
        }
    }

    /**
     * Retrieves all generators associated with a specific game from the database.
     *
     * @param id_game The ID of the game to retrieve generators for.
     * @return A list of generators associated with the game.
     * @throws PersistenceException If there is an error accessing the database.
     */
    @Override
    public List<Generator> getGeneratorsFromGame(int id_game) throws PersistenceException {
        List<Generator> generators = new ArrayList<>();
        String query = "SELECT g.id_generator, g.type AS generator_type, g.n_currencies, g.n_gens, g.photo AS generator_photo, g.improvement, " +
                "i.improvement_level, i.photo AS improvement_photo, i.improvement_type " +
                "FROM generator AS g INNER JOIN improvements AS i ON g.improvement = i.id_improvement " +
                "WHERE g.id_game = " + id_game;

        try (ResultSet result = SQLConnector.getInstance().selectQuery(query)) {
            while (result.next()) {
                Generator generator = createGeneratorFromResult(result, id_game);
                if (generator != null) {
                    generators.add(generator);
                }
            }
        } catch (SQLException e) {
            throw new ConnectionErrorException(e.getMessage());
        }

        return generators;
    }

    /**
     * Helper method to create a Generator object from a ResultSet.
     *
     * @param result  The ResultSet containing generator data.
     * @param id_game The ID of the game associated with the generators.
     * @return A Generator object created from the ResultSet.
     * @throws SQLException If there is an error accessing the ResultSet.
     */
    private Generator createGeneratorFromResult(ResultSet result, int id_game) throws SQLException {
        int id_generator = result.getInt("id_generator");
        String type = result.getString("generator_type");
        float n_currencies = result.getInt("n_currencies");
        int n_gens = result.getInt("n_gens");
        String imageUrl = result.getString("generator_photo");
        Improvement improvement = createImprovementFromResult(result);

        Generator generator = null;
        switch (type) {
            case "Basic":
                generator = new BasicGenerator(id_generator, n_currencies, id_game, n_gens, improvement, imageUrl);
                break;
            case "Mid":
                generator = new MidGenerator(id_generator, n_currencies, id_game, n_gens, improvement, imageUrl);
                break;
            case "High":
                generator = new HighGenerator(id_generator, n_currencies, id_game, n_gens, improvement, imageUrl);
                break;
        }
        return generator;
    }

    /**
     * Helper method to create an Improvement object from a ResultSet.
     *
     * @param result The ResultSet containing improvement data.
     * @return An Improvement object created from the ResultSet.
     * @throws SQLException If there is an error accessing the ResultSet.
     */
    private Improvement createImprovementFromResult(ResultSet result) throws SQLException {
        int id_improvement = result.getInt("improvement");
        int improvement_level = result.getInt("improvement_level");
        String improvementImageUrl = result.getString("improvement_photo");
        String improvementType = result.getString("improvement_type");

        switch (improvementType) {
            case "Basic":
                return new BasicImprovement(id_improvement, improvement_level, improvementImageUrl);
            case "Mid":
                return new MidImprovement(id_improvement, improvement_level, improvementImageUrl);
            case "High":
                return new HighImprovement(id_improvement, improvement_level, improvementImageUrl);
            default:
                return null;
        }
    }


}
