package store.controller;

import store.model.Cart;
import store.model.Item;
import store.model.Items;
import store.service.Payment;
import store.service.StockManage;
import store.service.promotion.PromotionResult;
import store.service.promotion.PromotionService;
import store.validator.Validator;
import store.view.ConvenienceView;

import java.util.HashMap;
import java.util.Map;

public class ConvenienceController {
    private static ConvenienceView view = new ConvenienceView();
    public void start() {
        StockManage stockManage = new StockManage();
        PromotionService promotionService = new PromotionService(stockManage);
        Items items = new Items();

        boolean repurchase = true;
        while (repurchase) {
            repurchase = isRepurchase(items, promotionService, repurchase);
        }
    }

    private static boolean isRepurchase(Items items, PromotionService promotionService, boolean repurchase) {
        Cart cart = new Cart();
        view.output.announceProduct(items);

        //사용자에게 구입할 물품 입력받기 ex) [콜라-11],[사이다-2]
        Map<String, Integer> inputItem = inputNameAndQuantity();
        inputItem = checkNoExistItem(inputItem, items);
        inputItem = checkOutOfStock(inputItem, items);

        //--------------------------------- Items에 있는 Item 탐색하며 구입할 물품 찾기
        for (String key : inputItem.keySet()) {
            for (Item item : items.getItems()) {
                if (item.getName().equals(key)) { // 아이템 찾기
                    PromotionResult result = promotionService.applyPromotion(item, inputItem.get(key));
                    inputItem.put(key, result.getRemainBuyQuantity());
                    if (inputItem.get(key) == 0) {
                        cart.addItem(item.getName(), result.getQuantityToCharge(), result.getBonusQuantity(), item.getPrice(), item.getPromotion());
                        break;
                    }
                }
            }
        }

        //멤버십 할인 및 계산
        Payment payment = new Payment(cart);
        payment.membershipSale();

        //영수증 출력 및 추가 구매
        repurchase = isRepurchaseAndPrintReceipt(cart, payment, repurchase);
        return repurchase;
    }

    private static boolean isRepurchaseAndPrintReceipt(Cart cart, Payment payment, boolean repurchase) {
        view.output.receipt(cart, payment);
        String addPurchase = view.input.additionalPurchaseOrNot();
        if (addPurchase.equalsIgnoreCase("Y")) {
            repurchase = true;
        }
        if (addPurchase.equalsIgnoreCase("N")) {
            repurchase = false;
        }
        return repurchase;
    }

    private static Map<String, Integer> checkNoExistItem(Map<String, Integer> inputItem, Items items) {
        while (true) {
            try {
                Validator.noExistItem(inputItem, items);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                inputItem = inputNameAndQuantity();
            }
        }
        return inputItem;
    }

    private static Map<String, Integer> checkOutOfStock(Map<String, Integer> inputItem, Items items) {
        while (true) {
            try {
                Validator.outOfStock(inputItem, items);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                inputItem = inputNameAndQuantity();
            }
        }
        return inputItem;
    }

    private static Map<String,Integer> inputNameAndQuantity() {
        Map<String, Integer> itemInfo = new HashMap<>();
        while (true) {
            String[] split = view.input.itemNameAndQuantity().split(",");
            try {
                for (String item : split) {
                    String[] parse = Validator.validateString(item);
                    Validator.validateInputItem(parse);
                    itemInfo.put(parse[0], itemInfo.getOrDefault(parse[0], 0) + Integer.parseInt(parse[1]));
                }
                return itemInfo;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
