package store.service;

public class Payment {
    private int totalPrice;
    private int bonusPrice;
    private int membershipDiscount;
    private int totalCount;

    public Payment(int totalPrice, int bonusPrice, int membershipDiscount, int totalCount) {
        this.totalPrice = totalPrice;
        this.bonusPrice = bonusPrice;
        this.membershipDiscount = membershipDiscount;
        this.totalCount = totalCount;
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
}
