package Business.Entities;
/*Class to save all the necessary information of the user playing*/
public class User {
    private String email;
    private String nickname;
    private String password;

    public User(String nickname, String email, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
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
