package store.validator;

import store.model.Item;
import store.model.Items;
import store.util.ErrorMessage;

import java.util.Map;

public class Validator {
    public static void validateInputItem(String[] parse) {
        try {
            Integer.parseInt(parse[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_FORMAT.getError());
        }
        if (parse.length != 2 || Integer.parseInt(parse[1]) <= 0) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_FORMAT.getError());
        }
    }

    public static void noExistItem(Map<String, Integer> inputItem, Items items) {
        for (String key : inputItem.keySet()) {
            boolean existItem = false;
            for (Item item : items.getItems()) {
                if (item.getName().equals(key)) {
                    existItem = true;
                }
            }
            if (!existItem) {
                throw new IllegalArgumentException(ErrorMessage.NON_EXISTENT_ITEM.getError());
            }
        }
    }

    public static void outOfStock(Map<String, Integer> inputItem, Items items) {
        for (String key : inputItem.keySet()) {
            int totalStock = 0;
            for (Item item : items.getItems()) {
                if (item.getName().equals(key)) {
                    totalStock += item.getQuantity();
                    if (totalStock < inputItem.get(key) && item.getPromotion().equals("null")) {
                        throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
                    }
                }
            }
        }
    }

    public static String[] validateString(String item) {
        String[] parse;
        try {
            parse = item.substring(1, item.length() - 1).split("-");
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT.getError());
        }
        return parse;
    }
}
