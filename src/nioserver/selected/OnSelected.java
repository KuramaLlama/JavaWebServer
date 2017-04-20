package nioserver.selected;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

public abstract class OnSelected {

    private Selector selector;

    public OnSelected(Selector selector) {
        this.selector = selector;
    }

    public Selector selector() {
        return selector;
    }

    public abstract void selected(SelectionKey selectionKey) throws IOException;
}
