package store.model;

import store.util.MarkdownLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
            items.add(new Item(itemInfo));
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

    public void printItem(int index) {
        System.out.println(items.get(index));
    }


    public int size() {
        return items.size();
    }

//    public int buyItem(String itemName, int quantity) {
//        for (Item item : items) {
//            if (item.getItemName().equals(itemName)) {
//                quantity = item.buyLogic(quantity);
//                System.out.println(item.getItemName()+" : "+item.getQuantity()+"개, 프로모션"+item.getPromotion());
//                if (quantity == 0) {
//                    return 0; //정상 종료
//                }
//            }
//        }
//        return 0;
//    }
}
