import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Ethereum extends Currency {
    private double gasPrice;
    private long totalSupply;

    public Ethereum(String name, String symbol, double price) {
        super(name, symbol, price);
    }

    public Ethereum(String name, String symbol, double price, long totalSupply, double gasPrice) {
        super(name, symbol, price);
        this.totalSupply = totalSupply;
        this.gasPrice = gasPrice;
    }

    @Override
    public void save() {
        String query = "INSERT INTO coins (name, symbol, price, gasPrice, totalSupply) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DbManager.getConnection();  // use a shared method
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, getName());
            stmt.setString(2, getSymbol());
            stmt.setDouble(3, getPrice());
            stmt.setDouble(4, gasPrice);
            stmt.setLong(5, totalSupply);

            stmt.executeUpdate();
            System.out.println("Ethereum inserted!");

        } catch (SQLException e) {
            System.err.println("Failed to insert Ethereum:");
            e.printStackTrace();
        }
    }


    public double getGasPrice() {
        return gasPrice;
    }

    public long getTotalSupply() {
        return totalSupply;
    }
}
