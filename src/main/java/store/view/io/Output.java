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
        System.out.println("==============W 편의점================");
        System.out.printf("%10s %10s %10s%n", "상품명", "수량", "금액");

        for (String name : cart.getOrder().keySet()) {
            int quantity = cart.getOrder().get(name);
            int price = quantity * cart.getPrice().get(name);
            System.out.printf("%-10s %10d %10s%n", name, quantity, String.format("%,d", price));
        }

        System.out.println("=============증    정===============");
        for (String name : cart.getFreeOrder().keySet()) {
            int freeQuantity = cart.getFreeOrder().get(name);
            if (freeQuantity > 0) {
                System.out.printf("%-10s %10d%n", name, freeQuantity);
            }
        }

        System.out.println("====================================");
        System.out.printf("%-10s %10d %10s%n", "총구매액", payment.getTotalCount(), String.format("%,d", payment.getTotalPrice()));
        System.out.printf("%-10s %20s%n", "행사할인", String.format("%,d", payment.getBonusPrice() * -1));
        System.out.printf("%-10s %20s%n", "멤버십할인", String.format("%,d", payment.getMembershipDiscount() * -1));
        System.out.printf("%-10s %20s%n", "내실돈", String.format("%,d", payment.finalPrice()));
    }
}
