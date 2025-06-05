public abstract class Currency {
    private String name;
    private String symbol;
    private double price;

    public Currency(String name, String symbol, double price) {
        this.name = name;
        this.symbol = symbol.toUpperCase();
        this.price = price;
    }

    public abstract void save();

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

}
