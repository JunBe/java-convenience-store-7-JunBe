package store.service.promotion;

public class PromotionResult {
    private int quantityToCharge;   // 최종 계산할 수량
    private int bonusQuantity;      // 증정되는 수량
    private int remainStock;  // 남은 재고
    private int remainBuyQuantity; //남은 구입 개수

    //remainingStock
    // 0 -> 재고 부족 X 다 살 수 있음 / 1 이상 -> 다음 물품으로 넘어가서 사야함
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

