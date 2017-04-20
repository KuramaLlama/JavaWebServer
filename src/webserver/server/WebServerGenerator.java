package webserver.server;

import nioserver.generators.ServerGenerator;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class WebServerGenerator implements ServerGenerator, Closeable {

    private ServerSocketChannel serverChannel = null;
    private Selector selector = null;
    private int port;

    public WebServerGenerator(int port) {
        this.port = port;
    }

    public boolean selectorIsOpen() {
        return selector.isOpen();
    }

    public boolean serverChannelIsOpen() {
        return serverChannel.isOpen();
    }

    public ServerSocketChannel generateServerChannel() {
        try {
            serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.bind(new InetSocketAddress(port));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverChannel;
    }

    public Selector generateSelector() {
        try {
            selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return selector;
    }

    @Override
    public void close() throws IOException {
        try {
            serverChannel.close();
            selector.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
