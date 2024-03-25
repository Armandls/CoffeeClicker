package Business.Entities;
/*Class to save all the necessary information of the user playing*/
public class User {
    private int id_user;
    private String email;
    private String nickname;
    private String password;

    public User(int id_user, String nickname, String email, String password) {
        this.id_user = id_user;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public int getIdUser() {
        return id_user;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }
}
