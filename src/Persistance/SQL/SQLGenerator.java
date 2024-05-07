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
import Persistance.Exception.NotFoundException;
import Persistance.Exception.PersistenceException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLGenerator implements GeneratorDAO {
    @Override
    public void addGenerator(Generator generator) throws ConnectionErrorException {
        //1- Inserim a la taula "Improvement" la millora associada al generador passat per paràmetres
        String improvementQuery = "INSERT INTO improvements(id_improvement, improvement_level, photo, improvement_type) VALUES ('" +
                generator.getImprovement().getIdImprovement() + "', '" +
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
        SQLConnector.getInstance().insertQuery(improvementQuery);

        //2- Una vegada tenim la referència de millora inserida a la base de dades podem afegir el generador
        String generatorQuery = "INSERT INTO generator(id_generator, n_currencies, id_game, n_gens, photo, type, improvement) VALUES ('" +
                generator.getIdGenerator() + "', '" +
                generator.getNCurrencies() + "', '" +
                generator.getIdGame() + "', '" +
                generator.getNGens() + "', '" +
                generator.getImageUrl() + "', ";
        if (generator instanceof BasicGenerator) {
            generatorQuery += "'Basic'";
        } else if (generator instanceof MidGenerator) {
            generatorQuery += "'Mid'";
        } else if (generator instanceof HighGenerator) {
            generatorQuery += "'High'";
        }
        generatorQuery += ", '" + generator.getImprovement().getIdImprovement() + "');"; // Include improvement
        SQLConnector.getInstance().insertQuery(generatorQuery);
    }
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
                "photo = '" + generator.getImageUrl() + "', " +
                "n_gens = '" + generator.getNGens() + "', " +
                "improvement = '" + generator.getImprovement().getIdImprovement() + "' " + // Include improvement
                "WHERE id_generator = '" + generator.getIdGenerator() + "';";
        try {
            SQLConnector.getInstance().updateQuery(generatorQuery);
        } catch (ConnectionErrorException e) {
            throw new ConnectionErrorException("Error updating generator with id <" + generator.getIdGenerator() + ">. " + e.getMessage());
        }
    }


    @Override
    public boolean deleteGenerator(int id_generator) throws ConnectionErrorException {
        // Delete cascade elimina millora associada al generador
        String query = "DELETE FROM generator WHERE id_generator = '" + id_generator + "';";
        try  {
            return SQLConnector.getInstance().deleteQuery(query);
        } catch (ConnectionErrorException e) {
            throw new ConnectionErrorException("Error deleting generator with id <" + id_generator + ">" + e.getMessage());
        }
    }


    @Override
    public List<Generator> getGeneratorsFromGame(int id_game) throws PersistenceException{
        List<Generator> generators = new ArrayList<>();

        String query = "SELECT g.id_generator, g.type AS generator_type, g.n_currencies, g.n_gens, g.photo AS generator_photo, g.improvement, " +
                "i.improvement_level, i.photo AS improvement_photo, i.improvement_type " +
                "FROM generator AS g INNER JOIN improvements AS i ON g.improvement = i.id_improvement " +
                "WHERE g.id_game = " + id_game;

        ResultSet result = SQLConnector.getInstance().selectQuery(query);

        try {
            while (result.next()) {
                // Atributs generator
                int id_generator = result.getInt("id_generator");
                String type = result.getString("generator_type");
                int n_currencies = result.getInt("n_currencies");
                int n_gens = result.getInt("n_gens");
                int id_improvement = result.getInt("improvement");
                String imageUrl = result.getString("generator_photo");
                // Atributs improvement
                int improvement_level = result.getInt("improvement_level");
                String improvementImageUrl = result.getString("improvement_photo");
                String improvementType = result.getString("improvement_type");

                Improvement improvement = null;

                // Es poden juntar switches però de moment ho deixem així per si escalem millores en un futur
                switch (improvementType) {
                    case "Basic":
                        improvement = new BasicImprovement(id_improvement, improvement_level, improvementImageUrl);
                        break;
                    case "Mid":
                        improvement = new MidImprovement(id_improvement, improvement_level, improvementImageUrl);
                        break;
                    case "High":
                        improvement = new HighImprovement(id_improvement, improvement_level, improvementImageUrl);
                        break;
                }

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
                if (improvement!= null) generator.setImprovement(improvement);
                if (generator != null) generators.add(generator);
            }
            if (generators.isEmpty()) {
                throw new NotFoundException("Generators not found for game with id " + id_game);
            }
        } catch (SQLException e) {
            throw new ConnectionErrorException(e.getMessage());
        }

        return generators;
    }

}
