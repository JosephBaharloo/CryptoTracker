import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class Altcoin extends Currency {
    private String blockchain;
    private boolean isStakeable;

    public Altcoin(String name, String symbol, double price) {
        super(name, symbol, price);
    }

    public Altcoin(String name, String symbol, double price, boolean isStakeable, String blockchain) {
        super(name, symbol, price);
        this.isStakeable = isStakeable;
        this.blockchain = blockchain;
    }

    @Override
    public void save() {
        String query = "INSERT INTO coins (name, symbol, price, blockchain, isstackable) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DbManager.getConnection();  // use a shared method
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, getName());
            stmt.setString(2, getSymbol());
            stmt.setDouble(3, getPrice());
            stmt.setString(4, blockchain);
            stmt.setBoolean(5, isStakeable);

            stmt.executeUpdate();
            System.out.println(getName() +  " inserted!");

        } catch (SQLException e) {
            System.err.println("Failed to insert "+ getName() + ":");
            e.printStackTrace();
        }
    }

    public String getBlockchain() {
        return blockchain;
    }

    public boolean isStakeable() {
        return isStakeable;
    }


}


