package store.model;

import store.view.ConvenienceView;

import java.util.ArrayList;
import java.util.List;

public class Item {
    private static final int ITEM_NAME = 0;
    private static final int ITEM_PRICE = 1;
    private static final int QUANTITY = 2;
    private static final int PROMOTION = 3;

    private List<String> item;

    public Item(List<String> itemInfo) {
        this.item = itemInfo;
    }

    public String getItemName() {
        return item.get(ITEM_NAME);
    }

    public int getPrice() {
        return Integer.parseInt(item.get(ITEM_PRICE));
    }

    public int getQuantity() {
        return Integer.parseInt(item.get(QUANTITY));
    }

    public String getPromotion() {
        return item.get(PROMOTION);
    }

    public List<String> getItem() {
        return item;
    }

    @Override
    public String toString() {
        return item.toString();
    }


    //    private String itemName;
//    private int price;
//    private int quantity;
//    private String promotion;
//    private int freeItem = 0;

    //    public int getFreeItem() {
//        return freeItem;
//    }
//    public int buyLogic(int quantity) {
//        //행사 상품인 경우
//        if (!this.promotion.equals("null")) { // 5 5 / 2 2
////            if (this.quantity < quantity) {
////                int param = quantity - this.quantity;
////                String purchaseYN = convenienceView.input.outOfStockPromotion(itemName, param);
////                if (purchaseYN.equals("Y")) {
////
////                }
////                if (purchaseYN.equals("N")) {
////                    return 0;
////                }
////            }
//            /**
//             * 재고 5 3인 경우
//             * -- 2 0
//             * 재고 6 3 인 경우
//             * -- 3 0
//             * 재고 7 3 인 경우
//             * -- 4 3
//             * 재고 5 2 인 경우 (this.quan>=3 && quan==2)
//             * -- 하나 더 드릴까요? Y : 2 0 / N : 3 0
//             * 재고 5 1 인 경우 (this.quan>=3 && quan==1)
//             * -- 4 0
//             * 재고 0 3 인 경우 (this.quan==0)
//             * -- return quan
//             * 재고 1 4 인 경우 (quan>=3 && this.quan>0)
//             * -- quan = quan- this.quan 하고 리턴 : 0 3
//             */
//            //2 3
//            if (this.promotion.equals("탄산2+1")) { //재고 9, 입력 4 / 6 1
//                while (this.quantity >= 3 && quantity >= 3) {
//                    this.quantity -= 3;
//                    quantity -= 3;
//                }
//                if (quantity == 0 || this.quantity == 0) {
//
//                    return quantity;
//                }
//                if (this.quantity >= 3 && quantity == 2) {
//                    String yesOrNo = convenienceView.input.lessThanPromotionQuantity(itemName);
//                    if (yesOrNo.equals("Y")) {
//                        this.quantity -= 3;
//                    }
//                    if (yesOrNo.equals("N")) {
//                        this.quantity -= 2;
//                    }
//                    return 0;
//                }
//                if (this.quantity >= 3 && quantity == 1) {
//                    this.quantity -= 1;
//                    return 0;
//                }
//                //quan>=3 && this.quan>0
//                //프로모션 재고가 부족해요 !!!
//                if (this.quantity > 0 && quantity >= 3) { //2 3
//                    quantity -= this.quantity;//0 1
//                    this.quantity = 0;
//                    return quantity;
//                }
//
//            }
//            if (this.promotion.equals("MD추천상품")) {
//
//            }
//            if (this.promotion.equals("반짝할인")) {
//
//            }
//
//
//            if (this.quantity < quantity) {
//                convenienceView.input.outOfStockPromotion(this.itemName, this.quantity);
//                return quantity;
//            }
//        }
//
//        //행사 상품이 아닌 경우
//        if (this.promotion.equals("null")) {
////            this.quantity-=quantity;
////            return 0;
//        }
//        System.out.println(this.quantity);
//
//
//        this.quantity -= quantity;
//        return 0;
//    }
}
