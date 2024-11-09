package store.model;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<String, Integer> order = new HashMap<>();
    private Map<String, Integer> freeOrder = new HashMap<>();
    private Map<String, Integer> price = new HashMap<>();

    private Map<String, Integer> promotion = new HashMap<>();


    private void addPrice(String name, int price) {
        this.price.put(name, this.price.getOrDefault(name, 0) + price);
    }

    private void addTotalItem(String name, int quantity) {
        order.put(name, order.getOrDefault(name, 0) + quantity);
    }

    private void addBonusItem(String name, int bonusQuantity) {
        freeOrder.put(name, freeOrder.getOrDefault(name, 0) + bonusQuantity);
    }

    private void addPromotion(String name, String promotion) {
        this.promotion.put(name, 1);
        if (promotion.equals("탄산2+1")) {
            this.promotion.put(name, 3);
        }
        if (promotion.equals("반짝할인") || promotion.equals("MD추천상품")) {
            this.promotion.put(name, 2);
        }
    }

    public Map<String, Integer> getOrder() {
        return order;
    }

    public Map<String, Integer> getFreeOrder() {
        return freeOrder;
    }

    public Map<String, Integer> getPrice() {
        return price;
    }

    public Map<String, Integer> getPromotion() {
        return promotion;
    }

    public void addItem(String name, int quantity, int bonusQuantity, int price, String promotion) {
        addTotalItem(name, quantity);
        addBonusItem(name, bonusQuantity);
        addPrice(name, price);
        addPromotion(name, promotion);
    }
}
