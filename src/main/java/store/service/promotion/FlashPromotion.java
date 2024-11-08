package store.service.promotion;

import store.model.Item;
import store.view.ConvenienceView;

public class FlashPromotion implements PromotionSelect {
    private static ConvenienceView view = new ConvenienceView();
    @Override
    public PromotionResult applyPromotion(Item item, int quantity, PromotionResult promotionResult) {
        int quantityToCharge = 0;
        int bonusQuantity = 0;
        int remainStock = item.getQuantity();
        int remainBuyQuantity = quantity;

        while (remainStock >= 2 && remainBuyQuantity >= 2) {
            bonusQuantity++;
            quantityToCharge += 2;
            remainStock -= 2;
            remainBuyQuantity -= 2;
        }

        if (remainStock == 0 || remainBuyQuantity == 0) {
            promotionResult.setAll(quantityToCharge, bonusQuantity, remainStock, remainBuyQuantity);
            return promotionResult;
        }

        if (remainStock <= remainBuyQuantity) {
            if (askBuyWithoutPromotion(item.getName(), remainBuyQuantity)) {
                quantityToCharge += remainStock;
                remainBuyQuantity -= remainStock;
                remainStock -= remainStock;
                promotionResult.setAll(quantityToCharge, bonusQuantity, remainStock, remainBuyQuantity);
                return promotionResult;
            }
            remainBuyQuantity = 0;
            promotionResult.setAll(quantityToCharge, bonusQuantity, remainStock, remainBuyQuantity);
            return promotionResult;
        }

        if (remainBuyQuantity == 1 && askGetPromotionItem(item.getName())) { //1+1
            bonusQuantity++;
            remainStock -= 2;
            quantityToCharge += 2;
            remainBuyQuantity -= 1;
            promotionResult.setAll(quantityToCharge, bonusQuantity, remainStock, remainBuyQuantity);
            return promotionResult;
        }

        promotionResult.setAll(quantityToCharge, bonusQuantity, remainStock, remainBuyQuantity);
        return promotionResult;
    }

    public static boolean askBuyWithoutPromotion(String name, int remainingQuantity) {
        String answer = view.input.outOfStockPromotion(name, remainingQuantity);
        return answer.equalsIgnoreCase("Y");
    }

    public static boolean askGetPromotionItem(String name) {
        String answer = view.input.canGetPromotionItem(name);
        return answer.equalsIgnoreCase("Y");
    }
}
