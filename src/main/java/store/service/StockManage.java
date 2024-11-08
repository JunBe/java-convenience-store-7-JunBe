package store.service;

import store.model.Item;
import store.service.promotion.PromotionResult;

public class StockManage {
    public PromotionResult updateStock(Item item, PromotionResult result) {
        item.setQuantity(result.getRemainStock());
        return result;
    }
}
