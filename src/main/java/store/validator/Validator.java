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

    public static void outOfStock() {
    }

}
