package store.model;

import store.util.MarkdownLoader;

import java.util.*;
import java.util.stream.Collectors;

public class Items {
    private static final int ITEM_NAME = 0;
    private static final int PRICE = 1;
    private static final int QUANTITY = 2;
    private static final int PROMOTION_NAME = 3;

    private List<Item> items;

    public Items() {
        this.items = new ArrayList<>();
        create();
    }

    private void create() {
        List<List<String>> itemStock = parseItems();
        Map<String, Boolean> itemHasNull = new HashMap<>();
        addItem(itemStock, itemHasNull);
        addNullItem(itemHasNull);
    }

    private void addItem(List<List<String>> itemStock, Map<String, Boolean> itemHasNull) {
        for (List<String> itemInfo : itemStock) {
            String name = itemInfo.get(ITEM_NAME);
            int price = Integer.parseInt(itemInfo.get(PRICE));
            int quantity = Integer.parseInt(itemInfo.get(QUANTITY));
            String promotion = itemInfo.get(PROMOTION_NAME);

            items.add(new Item(name, price, quantity, promotion));

            hasItemNull(itemHasNull, name, promotion);
        }
    }

    private static void hasItemNull(Map<String, Boolean> itemHasNull, String name, String promotion) {
        itemHasNull.putIfAbsent(name, false);
        if (promotion.equals("null")) {
            itemHasNull.put(name, true);
        }
    }

    private void addNullItem(Map<String, Boolean> itemHasNull) {
        for (Map.Entry<String, Boolean> entry : itemHasNull.entrySet()) {
            if (!entry.getValue()) {
                String name = entry.getKey();
                int price = items.stream()
                        .filter(item -> item.getName().equals(name))
                        .findFirst()
                        .map(Item::getPrice)
                        .orElse(0);
                items.add(new Item(name, price, 0, "null"));
            }
        }
    }

    private static List<List<String>> parseItems() {
        String markdownContent = MarkdownLoader.loadMarkdown("products.md");
        List<List<String>> itemStock = Arrays.stream(markdownContent.split("\n"))
                .map(line -> Arrays.asList(line.split(",")))
                .collect(Collectors.toList());
        itemStock.remove(0);
        return itemStock;
    }

    public List<Item> getItems() {
        return items;
    }

    public Item getItem(int index) {
        return items.get(index);
    }

    public int size() {
        return items.size();
    }


}
