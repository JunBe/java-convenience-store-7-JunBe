package store;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StockManageTest {

    @DisplayName("재고 수량이 부족하면 결제 불가")
    @Test
    void 재고_수량이_부족하면_결제_불가() {
        int quantity = 3;
        int orderQuantity = 4;

        Assertions.assertThatThrownBy(() -> {
            if (quantity < orderQuantity) {
                throw new IllegalArgumentException("[ERROR] 재고 수량이 부족합니다.");
            }
        }).isInstanceOf(IllegalArgumentException.class);
    }


    @DisplayName("재고 수량이 충분하면 결제 후 재고에서 차감")
    @Test
    void 재고_수량이_충분하면_결제_후_재고에서_차감() {
        int quantity = 3;
        int orderQuantity = 2;

        if (quantity >= orderQuantity) {
            quantity -= orderQuantity;
        }

        Assertions.assertThat(quantity).isEqualTo(1);
    }
}
