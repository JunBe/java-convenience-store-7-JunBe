package tdd;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.controller.ConvenienceController;
import store.util.ErrorMessage;
import store.validator.Validator;
import store.view.ConvenienceView;

public class InputTest {
    ConvenienceView view = new ConvenienceView();
    @DisplayName("구매할 상품 입력의 형식이 다를 경우 예외 발생")
    @Test
    void 구매할_상품_입력의_형식이_다를_경우_예외_발생() {
        Assertions.assertThatThrownBy(() -> {
            String input = "[콜라-3-예외]";
            String[] split = input.split(",");
            for (String item : split) {
                String[] parse = item.substring(1, item.length() - 1).split("-");
                if (parse.length != 2) {
                    throw new IllegalArgumentException(ErrorMessage.INVALID_FORMAT.getError());
                }
            }
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("구매할 상품의 개수가 양수가 아닌 경우 예외 발생")
    @Test
    void 구매할_상품의_개수가_양수가_아닌_경우_예외_발생() {
        Assertions.assertThatThrownBy(() -> {
            String input = "[콜라--3],[사이다-3],[에너지바-네개]";
            String[] split = input.split(",");
            for (String item : split) {
                String[] parse = item.substring(1, item.length() - 1).split("-");
                if (Integer.parseInt(parse[1]) <= 0) {
                    throw new IllegalArgumentException(ErrorMessage.INVALID_FORMAT.getError());
                }
            }
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("형식에 맞지 않는 입력 시 예외 발생")
    @Test
    void 형식에_맞지_않는_입력_시_예외_발생() {
        Assertions.assertThatThrownBy(() -> {
            String input = "t";
            String[] split = input.split(",");
            for (String item : split) {
                String[] parse = Validator.validateString(item);
                Validator.validateInputItem(parse);
            }
        }).isInstanceOf(IllegalArgumentException.class);
    }

}
