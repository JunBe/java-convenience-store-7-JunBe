package store.view.io;

import store.model.Cart;
import store.model.Item;
import store.model.Items;
import store.service.Payment;



public class Output {
    public void announceProduct(Items items) {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.");
        System.out.println();
        for (Item item : items.getItems()) {

            if (item.getPromotion().equals("null") && item.getQuantity() > 0) {
                System.out.println(String.format("- %s %,d원 %d개", item.getName(), item.getPrice(), item.getQuantity()));
                continue;
            }
            if (item.getPromotion().equals("null")) {
                System.out.println(String.format("- %s %,d원 재고 없음", item.getName(), item.getPrice()));
                continue;
            }
            if (item.getQuantity() > 0) {
                System.out.println(String.format("- %s %,d원 %d개 %s", item.getName(), item.getPrice(), item.getQuantity(), item.getPromotion()));
                continue;
            }
            System.out.println(String.format("- %s %,d원 재고 없음 %s", item.getName(), item.getPrice(), item.getPromotion()));


        }
        System.out.println();

    }

    public void receipt(Cart cart, Payment payment) {
        System.out.println("cart.getOrder() = " + cart.getOrder());
        System.out.println("cart.getPrice() = " + cart.getPrice());
        System.out.println("cart.getFreeOrder() = " + cart.getFreeOrder());
        System.out.println("==============W 편의점================");
        System.out.println("상품명\t\t수량\t금액");
        for (String name : cart.getOrder().keySet()) {
            System.out.println(name + "\t\t" + cart.getOrder().get(name) + "\t" +  String.format("%,d", cart.getOrder().get(name) * cart.getPrice().get(name)));
        }
        System.out.println("=============증\t정===============");
        for (String name : cart.getOrder().keySet()) {
            if (cart.getFreeOrder().get(name) > 0) {
                System.out.println(name + "\t\t" + cart.getFreeOrder().get(name));
            }
        }
        System.out.println("====================================");
        System.out.println("총구매액\t\t" + payment.getTotalCount() + "\t" +  String.format("%,d", payment.getTotalPrice()));
        System.out.println("행사할인\t\t" + "\t" + String.format("%,d", payment.getBonusPrice()*-1));
        System.out.println("멤버십할인\t\t" + "\t" + String.format("%,d", payment.getMembershipDiscount()*-1));
        System.out.println("내실돈\t\t" + "\t" +  String.format("%,d", payment.finalPrice()));
    }
}
