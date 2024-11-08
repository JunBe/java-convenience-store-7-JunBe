package store;

import store.model.Item;
import store.model.Items;
import store.service.DateCheck;
import store.service.StockManage;
import store.service.promotion.PromotionResult;
import store.service.promotion.PromotionService;
import store.view.ConvenienceView;

import java.util.*;

public class Application {
    public static void main(String[] args) {
        ConvenienceView view = new ConvenienceView();
        StockManage stockManage = new StockManage();
        PromotionService promotionService = new PromotionService(stockManage);

        //초기 재고 설정
        Items items = new Items();

        //사용자에게 구입할 물품 입력받기 ex) [콜라-11],[사이다-2]
        Map<String, Integer> inputItem = parsingNameAndQuantity(view.input.itemNameAndQuantity());

        //--------------------------------- Items에 있는 Item 탐색하며 구입할 물품 찾기
        for (String key : inputItem.keySet()) {
            for (Item item : items.getItems()) {
                if (item.getName().equals(key)) { // 아이템 찾기
                    PromotionResult result = promotionService.applyPromotion(item, inputItem.get(key));
                    System.out.println("한턴 끝난 후 살 개수 "+result.getRemainBuyQuantity());
                    System.out.println("프로모션 포함 " + result.getQuantityToCharge());
                    System.out.println("증정품" + result.getBonusQuantity());
                    inputItem.put(key, result.getRemainBuyQuantity());
                    if (inputItem.get(key) == 0) {
                        break;
                    }
                    if (inputItem.get(key) < 0) {
                        throw new IllegalArgumentException("재고가 0이하면 안됩니다.");
                    }
                }
            }
        }

        //목록 출력
        for (Item item : items.getItems()) {
            System.out.println(item);

        }
        //---------------------------------

        //멤버십 할인

        //영수증

//        int result = promotionService.applyPromotion(, 10);//item.promotion,[콜라-7],item.quantity
//        System.out.println(result); //0
    }

    private static Map<String,Integer> parsingNameAndQuantity(String input) {
        Map<String, Integer> itemInfo = new HashMap<>();
        String[] split = input.split(",");
        for (String item : split) {
            String[] parse = item.substring(1, item.length() - 1).split("-");
            itemInfo.put(parse[0], itemInfo.getOrDefault(parse[0], 0) + Integer.parseInt(parse[1]));
        }
        return itemInfo;
    }

}
