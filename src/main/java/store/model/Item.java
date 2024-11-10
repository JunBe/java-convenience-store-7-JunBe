package store.model;


public class Item {
    private final String name;
    private final int price;
    private int quantity;
    private final String promotion;

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPromotion() {
        return promotion;
    }

    public int getQuantity() {
        return quantity;
    }

    public Item(String name, int price, int quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    @Override
    public String toString() {
        return String.format("name='%s', price=%d, quantity=%d, promotion='%s'",
                name, price, quantity, promotion);
    }

    public String getName() {
        return name;
    }


    public int getPrice() {
        return price;
    }
}
