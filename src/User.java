import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private String email;
    private ArrayList<String> favoriteCoins;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.email = email;

    }
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.favoriteCoins = favoriteCoins;

    }


    public ArrayList<String> getFavoriteCoins() {
        return favoriteCoins;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
