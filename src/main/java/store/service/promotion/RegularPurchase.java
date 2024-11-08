package store.service.promotion;

import store.model.Item;

public class RegularPurchase implements PromotionSelect {
    @Override
    public PromotionResult applyPromotion(Item item, int quantity, PromotionResult promotionResult) {
        //
        System.out.println("RegularPurchase.applyPromotion 시작!!");
        int quantityToCharge = promotionResult.getQuantityToCharge();
        int bonusQuantity = promotionResult.getBonusQuantity();
        int remainStock = item.getQuantity(); //10
        int remainBuyQuantity = quantity; //7
        if (remainStock < remainBuyQuantity) { // 오류상황 10 11 5 6
            if (item.getPromotion().equals("null")) {
                throw new IllegalArgumentException("재고가 부족합니다. 다시입력하세요");
            }
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
