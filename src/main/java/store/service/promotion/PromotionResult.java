package store.service.promotion;

public class PromotionResult {
    private int quantityToCharge;
    private int bonusQuantity;
    private int remainStock;
    private int remainBuyQuantity;

    public PromotionResult(int quantityToCharge, int bonusQuantity, int remainStock, int remainBuyQuantity) {
        this.quantityToCharge = quantityToCharge;
        this.bonusQuantity = bonusQuantity;
        this.remainStock = remainStock;
        this.remainBuyQuantity = remainBuyQuantity;
    }

    public void setAll(int quantityToCharge, int bonusQuantity, int remainStock, int remainBuyQuantity) {
        this.quantityToCharge = quantityToCharge;
        this.bonusQuantity = bonusQuantity;
        this.remainStock = remainStock;
        this.remainBuyQuantity = remainBuyQuantity;
    }

    public int getQuantityToCharge() {
        return quantityToCharge;
    }

    public int getBonusQuantity() {
        return bonusQuantity;
    }

    public int getRemainStock() {
        return remainStock;
    }

    public int getRemainBuyQuantity() {
        return remainBuyQuantity;
    }
}

