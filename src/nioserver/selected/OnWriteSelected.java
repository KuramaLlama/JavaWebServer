package nioserver.selected;

import nioserver.channelio.OnWrite;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class OnWriteSelected extends OnSelected {

    private OnWrite onWrite;

    public OnWriteSelected(Selector writeSel, OnWrite onWrite) {
        super(writeSel);
        this.onWrite = onWrite;
    }

    @Override
    public void selected(SelectionKey selectionKey) throws IOException {
        onWrite.write((SocketChannel) selectionKey.channel(), (String) selectionKey.attachment());
    }
}
