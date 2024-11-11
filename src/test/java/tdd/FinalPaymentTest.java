package tdd;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FinalPaymentTest {

    @DisplayName("최종 결제 금액 계산")
    @Test
    void 최종_결제_금액_계산() {
        //given
        int totalPrice = 0;
        int price, orderQuantity, promotionQuantity;
        int promotionSalePrice = 0, membershipSalePrice = 0;
        String promotionSale, membershipSale;

        price = 3000;
        orderQuantity = 10;
        promotionQuantity=3;
        promotionSale = "Y";
        membershipSale = "Y";

        //when
        totalPrice = price * orderQuantity;
        if (promotionSale.equals("Y")) {
            promotionSalePrice = promotionQuantity / 3 * price;
        }

        if (membershipSale.equals("Y")) {
            membershipSalePrice = (totalPrice - (promotionSalePrice * 3)) * 3 / 10;
        }

        totalPrice = totalPrice - promotionSalePrice - membershipSalePrice;

        //then
        Assertions.assertThat(totalPrice).isEqualTo(20700);
    }
}
