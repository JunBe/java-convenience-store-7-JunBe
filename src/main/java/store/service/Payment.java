package store.service;

import store.model.Cart;
import store.view.ConvenienceView;

public class Payment {
    ConvenienceView view = new ConvenienceView();

    private int totalPrice=0;
    private int bonusPrice=0;
    private int membershipDiscount = 0;
    private int totalCount = 0;
    private int promotionPrice=0;
    private Cart cart;

    public Payment(Cart cart) {
        this.cart = cart;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getBonusPrice() {
        return bonusPrice;
    }

    public int getMembershipDiscount() {
        return membershipDiscount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int finalPrice() {
        return totalPrice - bonusPrice - membershipDiscount;
    }

    public void calculateLogic() {
        for (String key : cart.getFreeOrder().keySet()) {
            totalCount += cart.getOrder().get(key);
            totalPrice += cart.getOrder().get(key) * cart.getPrice().get(key);
            bonusPrice += cart.getFreeOrder().get(key) * cart.getPrice().get(key);
            if (cart.getFreeOrder().get(key) > 0) {
                promotionPrice += cart.getFreeOrder().get(key) * cart.getPrice().get(key) * cart.getPromotion().get(key);
            }

        }
    }

    public void membershipSale() {
        String membership = view.input.applyMembershipSaleOrNot();
        int membershipDiscountRate = 0;
        if (membership.equalsIgnoreCase("Y")) {
            membershipDiscountRate = 30;
        }
        calculateLogic();
        membershipDiscount(membershipDiscountRate);

    }
    public void membershipDiscount(int membershipDiscountRate) {
        membershipDiscount = (totalPrice - promotionPrice) * membershipDiscountRate / 100;
        membershipDiscount = Math.min(membershipDiscount, 8000);
    }
}
