import java.util.Scanner;
import java.util.ArrayList;


public class CryptoTracker {


    public static void main(String[] args) {

//        ++++++++++++++++ Server ++++++++++++++++

        Bitcoin btc = new Bitcoin("Bitcoin", "BTC", 104652, 64.54, 901254);
        Ethereum eth = new Ethereum("Ethereum", "eth", 2500, 1000000000, 0.005);
        Altcoin xrp = new Altcoin("Ripple", "xrp", 2.22);
        Altcoin ada = new Altcoin("Cardano", "ada", 0.7, true, "evm");

        btc.save();
        eth.save();
        xrp.save();
        ada.save();

//        ++++++++++++++++ user ++++++++++++++++
        Scanner scanner = new Scanner(System.in);
        String loggedInUser = "";
        DbManager dbManager = new DbManager();

        System.out.println("Welcome to Crypto Tracker\n1. Register\n2. Login ");
        int choice = scanner.nextInt();

//        if (choice == 1) {
//
//            System.out.println("Enter Username:");
//            String username = scanner.next();
//            loggedInUser = username;
//
//            System.out.println("Enter Password:");
//            String password = scanner.next();
//            String email;
//            scanner.nextLine();
//            while (true) {
//                System.out.println("Enter Email:");
//                email = scanner.nextLine();
//                if (email.equalsIgnoreCase("done") || email.isEmpty()) {
//                    break;
//                }
//                if (isEmailValid(email)) {
//                    break;
//                } else {
//                    System.out.println("Enter a valid email adress!");
//                }
//
//            }
//
//            User u1;
//            if (email.isEmpty()) {
//                u1 = new User(username, password); // use constructor without coins
//            } else {
//                u1 = new User(username, password, email);
//            }
//
//
//            dbManager.register(u1);
//
//        } else if (choice == 2) {
//            while (true){
//                scanner.nextLine();
//
//                System.out.println("Enter Username:");
//                String username = scanner.nextLine().replaceAll("\\s+", "").trim();
//
//                loggedInUser = username;
//
//                System.out.println("Enter Password:");
//                String password = scanner.next();
//
//
//                if (dbManager.login(username, password)) {
//                    System.out.println("Login successful!");
//                    break;
//                } else {
//                    System.out.println("Invalid username or password.");
//                }
//            }
//
//
//        } else {
//            System.out.println("Invalid choice");
//        }

        if (choice == 1) {
            loggedInUser = User.appRegister(scanner, dbManager);
        } else if (choice == 2) {
            loggedInUser = User.appLogin(scanner, dbManager);
        } else {
            System.out.println("Invalid choice!");
        }

        DbManager.displayAllCoins();


        System.out.println("Would you like to add favorite coins? (yes/no):");
        scanner.nextLine(); // clear buffer
        String response = scanner.nextLine().replaceAll("\\s+", "").trim().toLowerCase();

        // Use the logged-in username to add favorite coins
        if (response.equals("yes")) {
            dbManager.addFavoriteCoins(loggedInUser, scanner);


        }

    }
}
