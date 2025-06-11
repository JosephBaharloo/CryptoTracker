import java.util.ArrayList;
import java.util.Scanner;

public class User {
    private String username;
    private String password;
    private String email;
    private ArrayList<String> favoriteCoins;

    public User(String username, String password) {
        this.username = username;
        this.password = password;

    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;

    }

    public static String appRegister(Scanner scanner, DbManager dbManager) {
        System.out.println("Enter Username:");
        String username = scanner.next();

        System.out.println("Enter Password:");
        String password = scanner.next();
        String email;
        scanner.nextLine(); // consume remaining newline

        while (true) {
            System.out.println("Enter Email:");
            email = scanner.next();
            if (email.equalsIgnoreCase("null") || email.isEmpty()) {
                email = null; // Set to null if user enters 'null' or leaves it blank
                break;
            }
            if (isEmailValid(email)) {
                break;
            } else {
                System.out.println("Enter a valid email address!");
            }
        }

        User u1;
        if (email == null) {
            u1 = new User(username, password); // use constructor without email
        } else {
            u1 = new User(username, password, email);
        }

        dbManager.register(u1);
        return username;
    }

    public static String appLogin(Scanner scanner, DbManager dbManager) {
        while (true) {
            scanner.nextLine(); // consume any remaining newline

            System.out.println("Enter Username:");
            String username = scanner.nextLine().replaceAll("\\s+", "").trim();

            System.out.println("Enter Password:");
            String password = scanner.next();

            if (dbManager.login(username, password)) {
                System.out.println("Login successful!");
                return username;
            } else {
                System.out.println("Invalid username or password.");
            }
        }
    }

    private static boolean isEmailValid(String email) {
        // Add your email validation logic here
        return email.contains("@") && email.contains(".com") && email.indexOf("@") < email.indexOf(".com");
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
