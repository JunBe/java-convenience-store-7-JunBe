package store;

import store.model.Cart;
import store.model.Item;
import store.model.Items;
import store.service.DateCheck;
import store.service.Payment;
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

        //안내문
        boolean repurchase = true;
        while (repurchase) {
            Cart cart = new Cart();
            view.output.announceProduct(items);

            //사용자에게 구입할 물품 입력받기 ex) [콜라-11],[사이다-2]
            Map<String, Integer> inputItem = parsingNameAndQuantity(view.input.itemNameAndQuantity());

            //--------------------------------- Items에 있는 Item 탐색하며 구입할 물품 찾기
            for (String key : inputItem.keySet()) {

                //재고 수량 초과 로직
                int totalStock = 0;
                for (Item item : items.getItems()) {
                    if (item.getName().equals(key)) {
                        totalStock += item.getQuantity();
                        if (totalStock < inputItem.get(key) && item.getPromotion().equals("null")) {
                            System.out.println("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
//                            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
                        }
                    }
                }

                for (Item item : items.getItems()) {
                    if (item.getName().equals(key)) { // 아이템 찾기
                        PromotionResult result = promotionService.applyPromotion(item, inputItem.get(key));
                        inputItem.put(key, result.getRemainBuyQuantity());
                        if (inputItem.get(key) == 0) {
                            cart.addItem(item.getName(), result.getQuantityToCharge(), result.getBonusQuantity(), item.getPrice(), item.getPromotion());
                            break;
                        }
                        if (inputItem.get(key) < 0) {
                            throw new IllegalArgumentException("재고가 0이하면 안됩니다.");
                        }
                    }
                }
            }

            //목록 출력
//        for (Item item : items.getItems()) {
//            System.out.println(item);
//        }

            //멤버십 할인
            int membershipDiscountRate = 0;
            String membership = view.input.applyMembershipSaleOrNot();
            if (membership.equalsIgnoreCase("Y")) {
                membershipDiscountRate = 30;
            }

            //계산
            int totalPrice = 0; //총 구매액
            int bonusPrice = 0; //프로모션 제품 가격
            int totalCount = 0; //총 구매 수
            int bonusCount = 0; //증정 제품 수 /필요없음
            int promotionPrice = 0; // 프로모션 전체 가격
            int membershipDiscount = 0; //멤버십 할인
            for (String key : cart.getFreeOrder().keySet()) {
                totalCount += cart.getOrder().get(key);
                bonusCount += cart.getFreeOrder().get(key);
                totalPrice += cart.getOrder().get(key) * cart.getPrice().get(key);
                bonusPrice += bonusCount * cart.getPrice().get(key);
                //멤버십 할인 위함
                if (cart.getFreeOrder().get(key) > 0) {
                    promotionPrice += bonusPrice * cart.getPromotion().get(key);
                }

            }

            //멤버십할인계산
            membershipDiscount = (totalPrice - promotionPrice) * membershipDiscountRate / 100;
            membershipDiscount = Math.min(membershipDiscount, 8000);

            Payment payment = new Payment(totalPrice,bonusPrice,membershipDiscount,totalCount);

            //영수증
            view.output.receipt(cart, payment);

            //추가 구매
            String addPurchase = view.input.additionalPurchaseOrNot();
            if (addPurchase.equalsIgnoreCase("Y")) {
                repurchase = true;
            }
            if (addPurchase.equalsIgnoreCase("N")) {
                repurchase = false;
            }
        }
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
