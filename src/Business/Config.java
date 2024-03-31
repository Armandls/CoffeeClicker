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
    private String adminPwd;

    public Config(int port, String ip, String dataBaseName, String user, String pwd, String adminPwd) {
        this.port = port;
        this.ip = ip;
        this.dataBaseName = dataBaseName;
        this.user = user;
        this.pwd = pwd;
        this.adminPwd = adminPwd;
    }
}
