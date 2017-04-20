package nioserver.channelio;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public interface OnRead {
    String read(SocketChannel socketChannel) throws IOException;
}
