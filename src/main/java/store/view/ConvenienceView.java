package store.view;

import store.view.io.Input;
import store.view.io.Output;

public class ConvenienceView {
    public final Input input;
    public final Output output;

    public ConvenienceView() {
        this.input = new Input();
        this.output = new Output();
    }

}
