package store.model;

import store.util.MarkdownLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Items {
    private List<Item> items;


    public Items() {
        this.items = new ArrayList<>();
        create();
    }

    private void create() {
        List<List<String>> itemStock = parseItems();

        for (List<String> itemInfo : itemStock) {
            String name = itemInfo.get(0);
            int price = Integer.parseInt(itemInfo.get(1));
            int quantity = Integer.parseInt(itemInfo.get(2));
            String promotion = itemInfo.get(3);
            items.add(new Item(name, price, quantity, promotion));
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

    //아이템 찾기 로직
    public void findItem(Map<String, Integer> inputItem) {
        for (String key : inputItem.keySet()) {
            for (Item item : items) {
                if (item.getName().equals(key)) {
                    int remainingQuantity = item.applyPromotion(inputItem.get(key));
                    inputItem.put(key, remainingQuantity);
                    if (inputItem.get(key) == 0) {
                        return;
                    }
                    if (inputItem.get(key) < 0) {
                        throw new IllegalArgumentException("재고가 0이하면 안돼");
                    }
                }
            }
        }
    }


}
