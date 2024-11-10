package store;

import store.controller.ConvenienceController;
import store.view.ConvenienceView;

public class Application {
    public static ConvenienceView view = new ConvenienceView();
    public static void main(String[] args) {
        ConvenienceController controller = new ConvenienceController();
        controller.start();
    }
}
