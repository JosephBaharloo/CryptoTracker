import java.util.Scanner;
import java.util.ArrayList;


public class CryptoTracker {


    public static void main(String[] args) {

//        ++++++++++++++++ Server ++++++++++++++++

        Bitcoin btc = new Bitcoin("Bitcoin", "BTC", 104652, 64.54, 901254);
        Ethereum eth = new Ethereum("Ethereum", "eth", 2500, 1000000000, 0.005);
        Altcoin xrp = new Altcoin("Ripple", "xrp", 2.22);
        Altcoin DOGE = new Altcoin("Dogecoin", "DOGE", 0.1942);
        Altcoin ada = new Altcoin("Cardano", "ada", 0.7, true, "Ethereum");
        Altcoin USDT = new Altcoin("Tether", "USDT", 1.00, false, "Ethereum");
        Altcoin BNB = new Altcoin("BNB", "BNB", 665.61, true, "BNB");
        Altcoin sol = new Altcoin("Solana", "sol", 161.64, true, "Solana");
        Altcoin avax = new Altcoin("Avalanche", "avax", 21.66, false, "Avalanche");
        Altcoin XLM = new Altcoin("Stellar", "XLM", 0.2781, true, "Stellar");
        Altcoin trx = new Altcoin("TRON", "trx", 0.2779, false, "TRON");

        btc.save();
        eth.save();
        xrp.save();
        DOGE.save();
        ada.save();
        USDT.save();
        BNB.save();
        sol.save();
        avax.save();
        XLM.save();
        trx.save();

//        ++++++++++++++++ user ++++++++++++++++
        Scanner scanner = new Scanner(System.in);
        String loggedInUser = "";
        DbManager dbManager = new DbManager();

        System.out.println("Welcome to Crypto Tracker\n1. Register\n2. Login ");
        int choice = scanner.nextInt();

        if (choice == 1) {
            loggedInUser = User.appRegister(scanner, dbManager);
        } else if (choice == 2) {
            loggedInUser = User.appLogin(scanner, dbManager);
        } else {
            System.out.println("Invalid choice!");
        }

        DbManager.displayAllCoins();


        System.out.println("Would you like to add favorite coins? (yes/no):");
        scanner.nextLine();
        String response = scanner.nextLine().replaceAll("\\s+", "").trim().toLowerCase();


        if (response.equals("yes")) {
            dbManager.addFavoriteCoins(loggedInUser, scanner);


        }

    }
}
