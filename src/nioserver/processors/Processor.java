package nioserver.processors;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public interface Processor {
    void process(SocketChannel socketChannel) throws IOException;
}
