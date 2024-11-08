package store.service.promotion;

import store.model.Item;
import store.service.DateCheck;
import store.service.StockManage;

import java.util.HashMap;
import java.util.Map;

public class PromotionService {
    private final StockManage stockManage;
    private final Map<String, PromotionSelect> promotionSelect;
    DateCheck dateCheck = new DateCheck();
    private PromotionResult promotionResult = new PromotionResult(0, 0, 0, 0);

    public PromotionService(StockManage stockManage) {
        this.stockManage = stockManage;
        this.promotionSelect = new HashMap<>();
        this.promotionSelect.put("탄산2+1", new TwoPlusOnePromotion());
        promotionSelect.put("MD추천상품", new MDPromotion());
        promotionSelect.put("반짝할인", new FlashPromotion());
        promotionSelect.put("null", new RegularPurchase());
    }

    //item={[콜라,10,3000,2+1]} , 7
    public PromotionResult applyPromotion(Item item, int quantity) {
        if (promotionResult.getRemainBuyQuantity() == 0) {
            promotionResult = new PromotionResult(0, 0, 0, quantity);
        }

        PromotionSelect promotion = promotionSelect.get(item.getPromotion());
        if (!dateCheck.checkNowCanGet(item.getPromotion())) {
            System.out.println("기간이 종료됐습니다. " + item.getPromotion());
            promotion = promotionSelect.get("null");
        }

        if (promotion == null) {
            throw new IllegalArgumentException("[ERROR] 프로모션이 입력되지 않았습니다.");
        }

        promotionResult = promotion.applyPromotion(item, quantity, promotionResult); //TwpPlusOnePromotion.applyPromotion(item,7)
        PromotionResult finalResult = stockManage.updateStock(item, promotionResult);

        return finalResult;
    }

}
