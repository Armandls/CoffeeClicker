package Persistance.Config;

import Business.Config;
import Persistance.Exception.ConnectionErrorException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;

/**
 * Class responsible for reading the configuration from a JSON file.
 */
public class ConfigJSON {

    /**
     * Reads the configuration from the JSON file.
     * @return The configuration object.
     * @throws ConnectionErrorException If an error occurs while reading the configuration.
     */
    public static Config readConfigFile() throws ConnectionErrorException {
        try (FileReader reader = new FileReader("files/config.json")) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            int port = jsonObject.get("port").getAsInt();
            String ip = jsonObject.get("host").getAsString();
            String dbName = jsonObject.get("dbname").getAsString();
            String user = jsonObject.get("username").getAsString();
            String pwd = jsonObject.get("password").getAsString();
            return new Config(port, ip, dbName, user, pwd);
        } catch (IOException e) {
            throw new ConnectionErrorException(e.getMessage());
        }
    }
}
