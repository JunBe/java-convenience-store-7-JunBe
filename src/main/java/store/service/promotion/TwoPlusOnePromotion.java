package store.service.promotion;

import store.model.Item;
import store.model.StockManagement;
import store.view.ConvenienceView;

public class TwoPlusOnePromotion implements PromotionSelect{
    private static ConvenienceView view = new ConvenienceView();

    @Override
    public PromotionResult applyPromotion(Item item, int quantity, PromotionResult promotionResult) {
        StockManagement stock = new StockManagement(0, 0, item.getQuantity(), quantity);

        while (stock.getRemainStock() >= 3 && stock.getRemainBuyQuantity() >= 3) {
            stock.setBonusQuantity(stock.getBonusQuantity() + 1);
            stock.setQuantityToCharge(stock.getQuantityToCharge() + 3);
            stock.setRemainStock(stock.getRemainStock() - 3);
            stock.setRemainBuyQuantity(stock.getRemainBuyQuantity() - 3);
        }

        if (stock.getRemainStock() == 0 || stock.getRemainBuyQuantity() == 0) {
            promotionResult.setAll(stock.getQuantityToCharge(), stock.getBonusQuantity(), stock.getRemainStock(), stock.getRemainBuyQuantity());
            return promotionResult;
        }

        if (stock.getRemainStock() <= stock.getRemainBuyQuantity()) {
            if (askBuyWithoutPromotion(item.getName(), stock.getRemainBuyQuantity())) {
                stock.setQuantityToCharge(stock.getQuantityToCharge() + stock.getRemainStock());
                stock.setRemainBuyQuantity(stock.getRemainBuyQuantity() - stock.getRemainStock());
                stock.setRemainStock(0);
                promotionResult.setAll(stock.getQuantityToCharge(), stock.getBonusQuantity(), stock.getRemainStock(), stock.getRemainBuyQuantity());
                return promotionResult;
            }
            stock.setRemainBuyQuantity(0);
            promotionResult.setAll(stock.getQuantityToCharge(), stock.getBonusQuantity(), stock.getRemainStock(), stock.getRemainBuyQuantity());
            return promotionResult;
        }

        if (stock.getRemainBuyQuantity() == 1) {
            stock.setQuantityToCharge(stock.getQuantityToCharge() + 1);
            stock.setRemainBuyQuantity(stock.getRemainBuyQuantity() - 1);
            stock.setRemainStock(stock.getRemainStock() - 1);
            promotionResult.setAll(stock.getQuantityToCharge(), stock.getBonusQuantity(), stock.getRemainStock(), stock.getRemainBuyQuantity());
            return promotionResult;
        }

        if (stock.getRemainBuyQuantity() == 2) {
            stock.setQuantityToCharge(stock.getQuantityToCharge() + 2);
            stock.setRemainBuyQuantity(stock.getRemainBuyQuantity() - 2);
            stock.setRemainStock(stock.getRemainStock() - 2);

            if ((stock.getRemainStock() > stock.getRemainBuyQuantity()) && askGetPromotionItem(item.getName())) {
                stock.setQuantityToCharge(stock.getQuantityToCharge() + 1);
                stock.setBonusQuantity(stock.getBonusQuantity() + 1);
                stock.setRemainStock(stock.getRemainStock() - 1);
            }
            promotionResult.setAll(stock.getQuantityToCharge(), stock.getBonusQuantity(), stock.getRemainStock(), stock.getRemainBuyQuantity());
            return promotionResult;
        }

        promotionResult.setAll(stock.getQuantityToCharge(), stock.getBonusQuantity(), stock.getRemainStock(), stock.getRemainBuyQuantity());
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
