package store.model;

public class StockManagement {
    private int quantityToCharge;
    private int bonusQuantity;
    private int remainStock;
    private int remainBuyQuantity;

    public StockManagement(int quantityToCharge, int bonusQuantity, int remainStock, int remainBuyQuantity) {
        this.quantityToCharge = quantityToCharge;
        this.bonusQuantity = bonusQuantity;
        this.remainStock = remainStock;
        this.remainBuyQuantity = remainBuyQuantity;
    }

    public int getQuantityToCharge() {
        return quantityToCharge;
    }

    public void setQuantityToCharge(int quantityToCharge) {
        this.quantityToCharge = quantityToCharge;
    }

    public int getBonusQuantity() {
        return bonusQuantity;
    }

    public void setBonusQuantity(int bonusQuantity) {
        this.bonusQuantity = bonusQuantity;
    }

    public int getRemainStock() {
        return remainStock;
    }

    public void setRemainStock(int remainStock) {
        this.remainStock = remainStock;
    }

    public int getRemainBuyQuantity() {
        return remainBuyQuantity;
    }

    public void setRemainBuyQuantity(int remainBuyQuantity) {
        this.remainBuyQuantity = remainBuyQuantity;
    }
}
