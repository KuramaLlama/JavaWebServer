package nioserver.selected;

import nioserver.channelio.OnRead;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class OnReadSelected extends OnSelected{

    private Selector writeSel;
    private OnRead onRead;

    public OnReadSelected(Selector readSel, Selector writeSel, OnRead onRead) {
        super(readSel);
        this.writeSel = writeSel;
        this.onRead = onRead;
    }

    @Override
    public void selected(SelectionKey selectionKey) throws IOException {
        SelectionKey selKey = selectionKey.channel().register(writeSel, SelectionKey.OP_WRITE);
        selKey.attach(onRead.read((SocketChannel) selectionKey.channel()));
    }
}
