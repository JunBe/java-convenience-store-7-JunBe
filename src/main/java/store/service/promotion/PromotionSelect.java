package store.service.promotion;

import store.model.Item;

public interface PromotionSelect {
    PromotionResult applyPromotion(Item item, int quantity, PromotionResult promotionResult);
}
