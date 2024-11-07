package store.model;

import store.view.ConvenienceView;

public class Item {
    private final String name;
    private final int price;
    private int quantity;
    private final String promotion;

    private int bonusItem = 0;

    private ConvenienceView view = new ConvenienceView();

    public Item(String name, int price, int quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    @Override
    public String toString() {
        return String.format("name='%s', price=%d, quantity=%d, promotion='%s'",
                name, price, quantity, promotion);
    }

    public int applyPromotion(int requestedQuantity) {
        //날짜 비교도 추가할것
        if (promotion.equals("탄산2+1")) {
            return applyDrinkPromotion(requestedQuantity);
        }
        if (promotion.equals("MD추천상품")) {
            return applyMdRecommendedPromotion(requestedQuantity);
        }
        if (promotion.equals("반짝할인")) {
            return applyFlashDiscount(requestedQuantity);
        }
        if (promotion.equals("null")) {
            return regularPurchase(requestedQuantity);
        }
        return regularPurchase(requestedQuantity);
    }

    private int applyDrinkPromotion(int quantity) {
        bonusItem = 0;
        int remainingQuantity = quantity;
        while (this.quantity >= 3 && remainingQuantity >= 3) {
            bonusItem++;
            this.quantity -= 3;
            remainingQuantity -= 3;
        }

        if (remainingQuantity == 0 || this.quantity == 0) {
            return remainingQuantity;
        }

        if (this.quantity < remainingQuantity) {
            return askBuyWithoutPromotion(remainingQuantity);
        }
        if (remainingQuantity == 1) {
            this.quantity -= 1;
            return 0;
        }

        if (remainingQuantity == 2) {
            this.quantity -= 2;
            return askGetPromotionItem();
        }

        return remainingQuantity;
    }

    private int askGetPromotionItem() {
        String answer = view.input.canGetPromotionItem(name);
        if (answer.equals("Y")) {
            this.quantity -= 1;
            return 0;
        }
        if (answer.equals("N")) {
            return 0;
        }
        throw new IllegalArgumentException("다시 입력");
    }

    private int askBuyWithoutPromotion(int remainingQuantity) {
        String answer = view.input.outOfStockPromotion(name, remainingQuantity);

        if (answer.equals("Y")) {
            if (this.quantity >= remainingQuantity) { //4 2
                this.quantity -= remainingQuantity;
                return 0;
            }
            remainingQuantity -= this.quantity;
            this.quantity = 0;
            return remainingQuantity;
        }
        if (answer.equals("N")) {
            return remainingQuantity;
        }
        throw new IllegalArgumentException("다시 입력");
    }

    private int applyMdRecommendedPromotion(int quantity) {
        // MD 추천 상품 프로모션 로직 구현
        bonusItem = 0;
        int remainingQuantity = quantity;
        while (this.quantity >= 2 && remainingQuantity >= 2) {
            bonusItem++;
            this.quantity -= 2;
            remainingQuantity -= 2;
        }

        if (remainingQuantity == 0 || this.quantity == 0) {
            return remainingQuantity;
        }

        if (this.quantity < remainingQuantity) {
            return askBuyWithoutPromotion(remainingQuantity);
        }
        if (remainingQuantity == 1 && this.quantity == 1) {
            this.quantity -= 1;
            remainingQuantity -= 1;
            return remainingQuantity;
        }

        if (remainingQuantity == 1) {
            this.quantity -= 1;
            return askGetPromotionItem();
        }

        return remainingQuantity;
    }

    private int applyFlashDiscount(int quantity) {
        // 반짝 할인 상품 프로모션
        bonusItem = 0;
        int remainingQuantity = quantity;
        while (this.quantity >= 2 && remainingQuantity >= 2) {
            bonusItem++;
            this.quantity -= 2;
            remainingQuantity -= 2;
        }

        if (remainingQuantity == 0 || this.quantity == 0) {
            return remainingQuantity;
        }

        if (this.quantity < remainingQuantity) {
            return askBuyWithoutPromotion(remainingQuantity);
        }
        if (remainingQuantity == 1 && this.quantity == 1) {
            this.quantity -= 1;
            remainingQuantity -= 1;
            return remainingQuantity;
        }

        if (remainingQuantity == 1) {
            this.quantity -= 1;
            return askGetPromotionItem();
        }

        return remainingQuantity;
    }

    private int regularPurchase(int quantity) {
        if (this.quantity < quantity) {
            throw new IllegalArgumentException("재고가 부족합니다. 다시입력");
            //입력 받고 quantity에 저장?
        }
        this.quantity -= quantity;
        return 0;
    }

    public String getName() {
        return name;
    }

}
