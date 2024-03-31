package Business;

/**
 * This i sthe class that stores the information of the config file in RAM.
 */
public class Config {
    private int port;
    private String ip;
    private String dataBaseName;
    private String user;
    private String pwd;

    public Config(int port, String ip, String dataBaseName, String user, String pwd) {
        this.port = port;
        this.ip = ip;
        this.dataBaseName = dataBaseName;
        this.user = user;
        this.pwd = pwd;
    }

    public int getPort() {
        return port;
    }

    public String getIp() {
        return ip;
    }

    public String getDataBaseName() {
        return dataBaseName;
    }

    public String getUser() {
        return user;
    }

    public String getPwd() {
        return pwd;
    }
}
