package Business;

/**
 * This is the class that stores the information of the config file in RAM.
 */
public class Config {
    private final int port; // Port number
    private final String ip; // IP address
    private final String dataBaseName; // Database name
    private final String user; // Database username
    private final String pwd; // Database password

    /**
     * Constructor for the Config class.
     * @param port Port number.
     * @param ip IP address.
     * @param dataBaseName Database name.
     * @param user Database username.
     * @param pwd Database password.
     */
    public Config(int port, String ip, String dataBaseName, String user, String pwd) {
        this.port = port;
        this.ip = ip;
        this.dataBaseName = dataBaseName;
        this.user = user;
        this.pwd = pwd;
    }

    /**
     * Gets the port number.
     * @return The port number.
     */
    public int getPort() {
        return port;
    }

    /**
     * Gets the IP address.
     * @return The IP address.
     */
    public String getIp() {
        return ip;
    }

    /**
     * Gets the database name.
     * @return The database name.
     */
    public String getDataBaseName() {
        return dataBaseName;
    }

    /**
     * Gets the database username.
     * @return The database username.
     */
    public String getUser() {
        return user;
    }

    /**
     * Gets the database password.
     * @return The database password.
     */
    public String getPwd() {
        return pwd;
    }
}
