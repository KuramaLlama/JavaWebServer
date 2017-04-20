package nioserver.selected;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

public class ProcessSelected {

    public void selected(OnSelected onSelected) throws IOException {
        Selector selector = onSelected.selector();
        int keys = selector.select();

        if(keys > 0) {
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();

            while(selectionKeyIterator.hasNext()) {
                SelectionKey selectionKey = selectionKeyIterator.next();
                onSelected.selected(selectionKey);

                selectionKeyIterator.remove();
            }
            selectionKeys.clear();
        }
    }
}
