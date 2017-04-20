package nioserver.channelio;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public interface OnWrite {
    void write(SocketChannel socketChannel, String callback) throws IOException;
}
