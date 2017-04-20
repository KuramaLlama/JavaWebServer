package nioserver.acceptors;

import nioserver.generators.ServerGenerator;

import java.io.IOException;
import java.nio.channels.*;

public class ServerSocketChannelAcceptor implements Acceptor<SocketChannel> {

    private volatile boolean blocking = false;

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;

    public ServerSocketChannelAcceptor(ServerSocketChannel serverSocketChannel, Selector selector) {
        this.serverSocketChannel = serverSocketChannel;
        this.selector = selector;
        registerServerChannel(SelectionKey.OP_ACCEPT);
    }

    public ServerSocketChannelAcceptor(ServerGenerator serverGenerator) {
        serverSocketChannel = serverGenerator.generateServerChannel();
        selector = serverGenerator.generateSelector();
        registerServerChannel(SelectionKey.OP_ACCEPT);
    }

    public void configureBlocking(boolean blocking) {
        this.blocking = blocking;
    }

    @Override
    public SocketChannel accept() {
        SocketChannel socketChannel = null;
        try {
            selectKey();
            socketChannel = serverSocketChannel.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socketChannel;
    }

    private int selectKey() throws IOException {
        return (blocking) ? selector.select() : selector.selectNow();
    }

    private void registerServerChannel(int selKey) {
        try {
            serverSocketChannel.register(selector, selKey);
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        }
    }
}
