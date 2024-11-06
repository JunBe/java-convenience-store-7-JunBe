package store;

import camp.nextstep.edu.missionutils.DateTimes;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class DiscountPolicyTest {

    @DisplayName("탄산2+1 프로모션 할인")
    @Test
    void 탄산2_1_프로모션_할인() {
        String promotionSale = "Y";
        LocalDateTime now = DateTimes.now();
        LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 12, 31, 23, 59, 59);
        int promotionQuantity = 6;
        int itemPrice = 3000;
        int orderQuantity = 3;
        int promotionSalePrice = 0;
        if (orderQuantity % 3 != 0) {
            System.out.println("더 가져올건가요?");
        }

        if (promotionSale.equals("Y") && now.isAfter(startDate) && now.isBefore(endDate)) {
            promotionSalePrice = itemPrice * (orderQuantity / 3);
        }

        Assertions.assertThat(promotionSalePrice).isEqualTo(3000);
    }

    @DisplayName("MD추천상품 프로모션 할인")
    @Test
    void MD추천상품_프로모션_할인() {

        String promotionSale = "Y";
        LocalDateTime now = DateTimes.now();
        LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 12, 31, 23, 59, 59);
        int promotionQuantity = 6;
        int itemPrice = 3000;
        int orderQuantity = 4;
        int promotionSalePrice = 0;

        if (promotionQuantity < orderQuantity) {
            System.out.println("정가로 결제할건가요?");
            return;
        }

        if (orderQuantity % 2 != 0) {
            System.out.println("더 가져올건가요?");
            return;
        }

        if (promotionSale.equals("Y") && now.isAfter(startDate) && now.isBefore(endDate)) {
            promotionSalePrice = itemPrice * (orderQuantity / 2);
        }

        Assertions.assertThat(promotionSalePrice).isEqualTo(6000);
    }

    @DisplayName("반짝할인 프로모션 할인")
    @Test
    void 반짝할인_프로모션_할인() {
        String promotionSale = "Y";
        LocalDateTime now = DateTimes.now();
        LocalDateTime startDate = LocalDateTime.of(2024, 11, 1, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 11, 30, 23, 59, 59);
        int promotionQuantity = 6;
        int itemPrice = 3000;
        int orderQuantity = 6;
        int promotionSalePrice = 0;
        if (promotionQuantity < orderQuantity) {
            System.out.println("정가로 결제할건가요?");
            return;
        }

        if (orderQuantity % 2 != 0) {
            System.out.println("더 가져올건가요?");
            return;
        }

        if (promotionSale.equals("Y") && now.isAfter(startDate) && now.isBefore(endDate)) {
            promotionSalePrice = itemPrice * (orderQuantity / 2);
        }

        Assertions.assertThat(promotionSalePrice).isEqualTo(9000);
    }

}
