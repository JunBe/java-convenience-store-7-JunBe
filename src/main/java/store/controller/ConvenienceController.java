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
        Map<String, Integer> inputItem = inputNameAndQuantity();
        inputItem = checkNoExistItem(inputItem, items);
        inputItem = checkOutOfStock(inputItem, items);
        findBuyItem(items, promotionService, inputItem, cart);

        Payment payment = new Payment(cart);
        payment.membershipSale();

        return isRepurchaseAndPrintReceipt(cart, payment, repurchase);
    }

    private static void findBuyItem(Items items, PromotionService promotionService, Map<String, Integer> inputItem, Cart cart) {
        for (String key : inputItem.keySet()) {
            for (Item item : items.getItems()) {
                if (findItem(promotionService, inputItem, cart, key, item)) break;
            }
        }
    }

    private static boolean findItem(PromotionService promotionService, Map<String, Integer> inputItem, Cart cart, String key, Item item) {
        if (item.getName().equals(key)) {
            PromotionResult result = promotionService.applyPromotion(item, inputItem.get(key));
            inputItem.put(key, result.getRemainBuyQuantity());
            cart.addItem(item.getName(), result.getQuantityToCharge(), result.getBonusQuantity(), item.getPrice(), item.getPromotion()); //여기가 널
            if (inputItem.get(key) == 0) {
                return true;
            }
        }
        return false;
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

    private static Map<String,Integer> inputNameAndQuantity() { //고치기
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
