package store.model;

import store.util.MarkdownLoader;

import java.util.*;
import java.util.stream.Collectors;

public class Items {
    private List<Item> items;

    public Items() {
        this.items = new ArrayList<>();
        create();
    }

    private void create() {
        List<List<String>> itemStock = parseItems();
        Map<String, Boolean> itemHasNull = new HashMap<>();
        for (List<String> itemInfo : itemStock) {
            String name = itemInfo.get(0);
            int price = Integer.parseInt(itemInfo.get(1));
            int quantity = Integer.parseInt(itemInfo.get(2));
            String promotion = itemInfo.get(3);
            items.add(new Item(name, price, quantity, promotion));

            itemHasNull.putIfAbsent(name, false); //f
            if (promotion.equals("null")) {
                itemHasNull.put(name, true);
            }
        }

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
