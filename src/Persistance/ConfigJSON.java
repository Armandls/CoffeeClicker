package Persistance;

import Business.Config;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;

public class ConfigJSON implements ConfigDAO {

    public Config readConfigFile() {
        try (FileReader reader = new FileReader("files/config.json")) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            int port = jsonObject.get("port").getAsInt();
            String ip = jsonObject.get("host").getAsString();
            String dbName = jsonObject.get("dbname").getAsString();
            String user = jsonObject.get("username").getAsString();
            String pwd = jsonObject.get("password").getAsString();
            return new Config(port, ip, dbName, user, pwd);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}