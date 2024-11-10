package store.service.promotion;

import store.model.Item;
import store.view.ConvenienceView;

public class TwoPlusOnePromotion implements PromotionSelect{
    private static ConvenienceView view = new ConvenienceView();
    @Override
    public PromotionResult applyPromotion(Item item, int quantity,PromotionResult promotionResult) {
        int quantityToCharge = 0;
        int bonusQuantity = 0;
        int remainStock = item.getQuantity(); //10
        int remainBuyQuantity = quantity; //7

        while (remainStock >= 3 && remainBuyQuantity >= 3) {
            bonusQuantity++; //2
            quantityToCharge += 3; //6
            remainStock -= 3; //4
            remainBuyQuantity -= 3; //1
        }//1 5

        if (remainStock == 0 || remainBuyQuantity == 0) { //9 6 / 6 3 / 3 0
            promotionResult.setAll(quantityToCharge, bonusQuantity, remainStock, remainBuyQuantity);
            return promotionResult;
        }//6,2,3,0

        if (remainStock < remainBuyQuantity) { //10 14 1 5
            if (askBuyWithoutPromotion(item.getName(), remainBuyQuantity)) { // 정상가 구매?
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

        if (remainBuyQuantity == 1) { //7 4 -> 4 1
            quantityToCharge += 1;
            remainBuyQuantity -= 1;
            remainStock -= 1;
            promotionResult.setAll(quantityToCharge, bonusQuantity, remainStock, remainBuyQuantity);
            return promotionResult;
        }

        if (remainBuyQuantity == 2) { //5 2 3 2 2 2
            quantityToCharge += 2;
            remainBuyQuantity -= 2; // 0
            remainStock -= 2;
            if ((remainStock > remainBuyQuantity) && askGetPromotionItem(item.getName())) {
                quantityToCharge += 1;
                bonusQuantity++;
                remainStock--;
            }
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
