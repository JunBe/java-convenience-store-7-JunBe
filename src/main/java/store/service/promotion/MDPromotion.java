package store.service.promotion;

import store.model.Item;
import store.model.StockManagement;
import store.view.ConvenienceView;

public class MDPromotion implements PromotionSelect {
    private static ConvenienceView view = new ConvenienceView();
    @Override
    public PromotionResult applyPromotion(Item item, int quantity, PromotionResult promotionResult) {
        StockManagement stock = new StockManagement(0, 0, item.getQuantity(), quantity);

        return getPromotionResult(item, promotionResult, stock);
    }

    private static PromotionResult getPromotionResult(Item item, PromotionResult promotionResult, StockManagement stock) {
        flow1(stock);

        PromotionResult flow2 = flow2(promotionResult, stock);
        if (flow2 != null) return flow2;

        PromotionResult flow3 = flow3(item, promotionResult, stock);
        if (flow3 != null) return flow3;

        PromotionResult flow4 = flow4(item, promotionResult, stock);
        if (flow4 != null) return flow4;

        promotionResult.setAll(stock.getQuantityToCharge(), stock.getBonusQuantity(), stock.getRemainStock(), stock.getRemainBuyQuantity());
        return promotionResult;
    }

    private static PromotionResult flow4(Item item, PromotionResult promotionResult, StockManagement stock) {
        if (stock.getRemainBuyQuantity() == 1) {
            if (askGetPromotionItem(item.getName())) {
                stock.setBonusQuantity(stock.getBonusQuantity() + 1);
                stock.setRemainStock(stock.getRemainStock() - 2);
                stock.setQuantityToCharge(stock.getQuantityToCharge() + 2);
                stock.setRemainBuyQuantity(stock.getRemainBuyQuantity() - 1);
                promotionResult.setAll(stock.getQuantityToCharge(), stock.getBonusQuantity(), stock.getRemainStock(), stock.getRemainBuyQuantity());
                return promotionResult;
            }
            stock.setRemainStock(stock.getRemainStock() - 1);
            stock.setQuantityToCharge(stock.getQuantityToCharge() + 1);
            stock.setRemainBuyQuantity(stock.getRemainBuyQuantity() - 1);
        }
        return null;
    }

    private static PromotionResult flow3(Item item, PromotionResult promotionResult, StockManagement stock) {
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
        return null;
    }

    private static PromotionResult flow2(PromotionResult promotionResult, StockManagement stock) {
        if (stock.getRemainStock() == 0 || stock.getRemainBuyQuantity() == 0) {
            promotionResult.setAll(stock.getQuantityToCharge(), stock.getBonusQuantity(), stock.getRemainStock(), stock.getRemainBuyQuantity());
            return promotionResult;
        }
        return null;
    }

    private static void flow1(StockManagement stock) {
        while (stock.getRemainStock() >= 2 && stock.getRemainBuyQuantity() >= 2) {
            stock.setBonusQuantity(stock.getBonusQuantity() + 1);
            stock.setQuantityToCharge(stock.getQuantityToCharge() + 2);
            stock.setRemainStock(stock.getRemainStock() - 2);
            stock.setRemainBuyQuantity(stock.getRemainBuyQuantity() - 2);
        }
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
