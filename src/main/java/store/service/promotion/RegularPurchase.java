package store.service.promotion;

import store.model.Item;

public class RegularPurchase implements PromotionSelect {
    @Override
    public PromotionResult applyPromotion(Item item, int quantity, PromotionResult promotionResult) {
        int quantityToCharge = promotionResult.getQuantityToCharge();
        int bonusQuantity = promotionResult.getBonusQuantity();
        int remainStock = item.getQuantity();
        int remainBuyQuantity = quantity;
        if (remainStock < remainBuyQuantity) {
            quantityToCharge += remainStock;
            remainBuyQuantity -= remainStock;
            remainStock = 0;
            promotionResult.setAll(quantityToCharge, bonusQuantity, remainStock, remainBuyQuantity);
            return promotionResult;
        }
        remainStock -= remainBuyQuantity;
        quantityToCharge += remainBuyQuantity;
        remainBuyQuantity -= remainBuyQuantity;
        promotionResult.setAll(quantityToCharge, bonusQuantity, remainStock, remainBuyQuantity);
        return promotionResult;
    }
}
