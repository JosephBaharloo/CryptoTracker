import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;


public class Bitcoin extends Currency {
    private double hashRate;
    private int blockHeight;

    public Bitcoin(String name, String symbol, double price) {
        super(name, symbol, price);
    }


    public Bitcoin(String name, String symbol, double price, double hashRate, int blockHeight) {
        super(name, symbol, price);
        this.blockHeight = blockHeight;
        this.hashRate = hashRate;
    }

    @Override
    public void save() {
        String query = "INSERT INTO coins (name, symbol, price, hashRate, blockHeight) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, getName());
            stmt.setString(2, getSymbol());
            stmt.setDouble(3, getPrice());
            stmt.setDouble(4, hashRate);
            stmt.setInt(5, blockHeight);

            stmt.executeUpdate();
            System.out.println("Bitcoin inserted!");

        } catch (SQLException e) {
            System.err.println("Failed to insert Bitcoin:");
            e.printStackTrace();
        }
    }


    public double getHashRate() {
        return hashRate;
    }

    public int getBlockHeight() {
        return blockHeight;
    }


}
