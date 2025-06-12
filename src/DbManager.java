import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class DbManager implements DbOpperations {
    static String username = "root";
    static String password = "123456";
    static String url = "jdbc:mysql://localhost:3306/crypto";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public void showError(SQLException e) {
        System.out.println("Error: " + e.getMessage());
        System.out.println("Error Code: " + e.getErrorCode());
    }

    @Override
    public void register(User user) {
        String query = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean login(String _username, String _password) {
        String query = "SELECT password FROM users WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, _username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                return storedPassword.equals(_password);
            } else {
                System.out.println("Username not found");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void displayAllCoins() {
        String query = "SELECT * FROM coins";

        try (Connection conn = DbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("| %-15s | %-10s | %-10s | %-10s | %-12s | %-10s | %-14s | %-11s | %-15s |\n",
                    "Name", "Symbol", "Price", "HashRate", "BlockHeight", "GasPrice", "TotalSupply", "Stackable", "blockchain");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");

            while (rs.next()) {
                String name = rs.getString("name");
                String symbol = rs.getString("symbol");
                double price = rs.getDouble("price");
                String hashRate = rs.getObject("hashRate") != null ? String.valueOf(rs.getDouble("hashRate")) : "N/A";
                String blockHeight = rs.getObject("blockHeight") != null ? String.valueOf(rs.getInt("blockHeight")) : "N/A";
                String gasPrice = rs.getObject("gasPrice") != null ? String.valueOf(rs.getDouble("gasPrice")) : "N/A";
                String totalSupply = rs.getObject("totalSupply") != null ? String.valueOf(rs.getLong("totalSupply")) : "N/A";
                String isStackable = rs.getObject("isStackable") != null ? (rs.getBoolean("isStackable") ? "Yes" : "No") : "N/A";
                String blockchain = rs.getString("blockchain");

                System.out.printf("| %-15s | %-10s | %-10.2f | %-10s | %-12s | %-10s | %-14s | %-11s | %-15s |\n",
                        name, symbol, price, hashRate, blockHeight, gasPrice, totalSupply, isStackable, blockchain);
            }

            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("Failed to fetch data from database:");
            e.printStackTrace();
        }
    }

    public void addFavoriteCoins(String username, Scanner scanner) {
        ArrayList<String> newFavorites = new ArrayList<>();

        System.out.println("Enter symbols of favorite coins (type 'done' to finish):");
        while (true) {
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("DONE")) {
                break;
            }

            if (!input.isEmpty()) {
                newFavorites.add(input);
            }
        }

        if (newFavorites.isEmpty()) {
            System.out.println("No coins were added.");
            return;
        }

        try (Connection conn = getConnection()) {
            String selectQuery = "SELECT favorite_coins FROM users WHERE username = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
            selectStmt.setString(1, username);
            ResultSet rs = selectStmt.executeQuery();
            Gson gson = new Gson();

            if (rs.next()) {
                String currentCoins = rs.getString("favorite_coins");
                ArrayList<String> coinList = new ArrayList<>();

                if (currentCoins != null && !currentCoins.isEmpty()) {
                    // Parse JSON to ArrayList
                    String[] coinsArray = gson.fromJson(currentCoins, String[].class);
                    if (coinsArray != null) {
                        for (String coin : coinsArray) {
                            coinList.add(coin);
                        }
                    }
                }

                for (String coin : newFavorites) {
                    if (!coinList.contains(coin)) {
                        coinList.add(coin);
                    }
                }

                String updatedCoins = gson.toJson(coinList);

                String updateQuery = "UPDATE users SET favorite_coins = ? WHERE username = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setString(1, updatedCoins);
                updateStmt.setString(2, username);
                updateStmt.executeUpdate();

                System.out.println("Favorite coins updated successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
