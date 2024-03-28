package Persistance.SQL;

import Business.Entities.Generator.BasicGenerator;
import Business.Entities.Generator.Generator;
import Business.Entities.Generator.HighGenerator;
import Business.Entities.Generator.MidGenerator;
import Persistance.GeneratorDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLGenerator implements GeneratorDAO {
    @Override
    public void addGenerator(Generator generator) {
        String query = "INSERT INTO Generator(id_generator, type, n_boosts, n_currencies) VALUES ('" +
                generator.getIdGenerator() + "', '" +
                generator.getNBoosts() + "', '" +
                generator.getNCurrencies() + "', ";
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
        String query = "SELECT id_generator, type, n_boosts, n_currencies FROM Generator WHERE id_generator = " + id_generator;
        ResultSet result = SQLConnector.getInstance().selectQuery(query);
        try {
            int generator_id = result.getInt("id_generator");
            String type = result.getString("type");
            int n_boosts = result.getInt("n_boosts");
            int n_currencies = result.getInt("n_currencies");

            switch (type) {
                case "Basic":
                    return new BasicGenerator(generator_id, n_boosts, n_currencies);
                case "Mid":
                    return new MidGenerator(generator_id, n_boosts, n_currencies);
                case "High":
                    return new HighGenerator(generator_id, n_boosts, n_currencies);
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
                "n_currencies = '" + generator.getNCurrencies() + "' " +
                "WHERE id_generator = '" + generator.getIdGenerator() + "';";

        SQLConnector.getInstance().updateQuery(query);

    }

    @Override
    public boolean deleteGenerator(int id_generator) {
        String query = "DELETE FROM Generator WHERE id_generator = '" + id_generator + "';";
        return SQLConnector.getInstance().deleteQuery(query);    }

    @Override
    public void addGeneratorToGame(int id_game, int id_generator) {
        String query = "INSERT INTO Game_gen (id_game, id_gen) VALUES ('" + id_game + "', '" + id_generator + "');";

        SQLConnector.getInstance().insertQuery(query);
    }

    @Override
    public List<Generator> getGeneratorsFromGame(int id_game) {
        List<Generator> generators = new ArrayList<>();

        String query = "SELECT * FROM Generator AS gen " +
                "JOIN Game_gen AS game_gen ON gen.id_generator = game_gen.id_gen " +
                "WHERE game_gen.id_game = " + id_game + ";";

        ResultSet result = SQLConnector.getInstance().selectQuery(query);

        try {
            while (result.next()) {
                int id_generator = result.getInt("id_generator");
                String type = result.getString("type");
                int n_boosts = result.getInt("n_boosts");
                int n_currencies = result.getInt("n_currencies");

                Generator generator = null;
                switch (type) {
                    case "Basic":
                        generator = new BasicGenerator(id_generator, n_boosts, n_currencies);
                        break;
                    case "Mid":
                        generator = new MidGenerator(id_generator, n_boosts, n_currencies);
                        break;
                    case "High":
                        generator = new HighGenerator(id_generator, n_boosts, n_currencies);
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
