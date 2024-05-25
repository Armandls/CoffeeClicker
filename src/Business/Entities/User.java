package Business.Entities;

/**
 * Clase para almacenar toda la información necesaria del usuario que juega.
 */
public class User {
    private String email; // Correo electrónico del usuario
    private String nickname; // Apodo del usuario
    private String password; // Contraseña del usuario

    /**
     * Constructor de la clase User.
     * @param nickname Apodo del usuario.
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     */
    public User(String nickname, String email, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     * @return Correo electrónico del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Obtiene el apodo del usuario.
     * @return Apodo del usuario.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Obtiene la contraseña del usuario.
     * @return Contraseña del usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece el apodo del usuario.
     * @param nickname Apodo del usuario.
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Establece la contraseña del usuario.
     * @param password Contraseña del usuario.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Establece el correo electrónico del usuario.
     * @param email Correo electrónico del usuario.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
