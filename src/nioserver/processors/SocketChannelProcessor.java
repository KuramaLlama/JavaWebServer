package nioserver.processors;

import nioserver.selected.OnSelected;
import nioserver.selected.ProcessSelected;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class SocketChannelProcessor implements Processor {

    private OnSelected onRead, onWrite;
    private ProcessSelected processSelected;

    public SocketChannelProcessor(OnSelected onRead, OnSelected onWrite, ProcessSelected processSelected) {
        this.onRead = onRead;
        this.onWrite = onWrite;
        this.processSelected = processSelected;
    }

    @Override
    public void process(SocketChannel socketChannel) throws IOException {
        socketChannel.configureBlocking(false);
        socketChannel.register(onRead.selector(), SelectionKey.OP_READ);
        processSelected.selected(onRead);
        processSelected.selected(onWrite);
    }
}



