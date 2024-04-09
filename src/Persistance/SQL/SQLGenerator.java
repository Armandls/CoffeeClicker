package Persistance.SQL;

import Business.Entities.Generator.BasicGenerator;
import Business.Entities.Generator.Generator;
import Business.Entities.Generator.HighGenerator;
import Business.Entities.Generator.MidGenerator;
import Persistance.DAO.GeneratorDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLGenerator implements GeneratorDAO {
    @Override
    public void addGenerator(Generator generator) {
        String query = "INSERT INTO Generator(id_generator, n_boosts, n_currencies, id_game, n_gens, image, type) VALUES ('" +
                generator.getIdGenerator() + "', '" +
                generator.getNBoosts() + "', '" +
                generator.getNCurrencies() + "', '" +
                generator.getIdGame() + "', '" +
                generator.getNGens() + "', '" +
                generator.getImageUrl() + "', ";
        if (generator instanceof BasicGenerator) {
            query += "'Basic'";
        } else if (generator instanceof MidGenerator) {
            query += "'Mid'";
        } else if (generator instanceof HighGenerator) {
            query += "'High'";
        }
        query += ");";
        SQLConnector.getInstance().insertQuery(query);
    }


    @Override
    public Generator getGenerator(int id_generator) {
        String query = "SELECT id_generator, type, n_boosts, n_currencies, id_game, image, n_gens FROM Generator WHERE id_generator = " + id_generator;
        ResultSet result = SQLConnector.getInstance().selectQuery(query);
        try {
            if (result.next()) {
                int generator_id = result.getInt("id_generator");
                String type = result.getString("type");
                int n_boosts = result.getInt("n_boosts");
                int n_currencies = result.getInt("n_currencies");
                int id_game = result.getInt("id_game");
                String imageUrl = result.getString("image");
                int n_gens = result.getInt("n_gens");

                switch (type) {
                    case "Basic":
                        return new BasicGenerator(generator_id, n_boosts, n_currencies, id_game, n_gens, imageUrl);
                    case "Mid":
                        return new MidGenerator(generator_id, n_boosts, n_currencies, id_game, n_gens, imageUrl);
                    case "High":
                        return new HighGenerator(generator_id, n_boosts, n_currencies, id_game, n_gens, imageUrl);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void updateGenerator(Generator generator) {
        String query = "UPDATE Generator SET " +
                "n_boosts = '" + generator.getNBoosts() + "', " +
                "n_currencies = '" + generator.getNCurrencies() + "', " +
                "id_game = '" + generator.getIdGame() + "', " +
                "image = '" + generator.getImageUrl() + "', " +
                "n_gens = '" + generator.getNGens() + "' " +
                "WHERE id_generator = '" + generator.getIdGenerator() + "';";

        SQLConnector.getInstance().updateQuery(query);
    }


    @Override
    public boolean deleteGenerator(int id_generator) {
        String query = "DELETE FROM Generator WHERE id_generator = '" + id_generator + "';";
        return SQLConnector.getInstance().deleteQuery(query);    }


    @Override
    public List<Generator> getGeneratorsFromGame(int id_game) {
        List<Generator> generators = new ArrayList<>();

        String query = "SELECT * FROM Generator WHERE id_game = " + id_game;

        ResultSet result = SQLConnector.getInstance().selectQuery(query);

        try {
            while (result.next()) {
                int id_generator = result.getInt("id_generator");
                String type = result.getString("type");
                int n_boosts = result.getInt("n_boosts");
                int n_currencies = result.getInt("n_currencies");
                int n_gens = result.getInt("n_gens");
                String imageUrl = result.getString("image");

                Generator generator = null;
                switch (type) {
                    case "Basic":
                        generator = new BasicGenerator(id_generator, n_boosts, n_currencies, id_game, n_gens, imageUrl);
                        break;
                    case "Mid":
                        generator = new MidGenerator(id_generator, n_boosts, n_currencies, id_game, n_gens, imageUrl);
                        break;
                    case "High":
                        generator = new HighGenerator(id_generator, n_boosts, n_currencies, id_game, n_gens, imageUrl);
                        break;
                }
                if (generator != null) {
                    generators.add(generator);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return generators;
    }



}
