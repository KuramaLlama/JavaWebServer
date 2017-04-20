package nioserver.channelio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelReader implements OnRead {

    private ByteBuffer readBuffer;

    public SocketChannelReader(ByteBuffer readBuffer) {
        this.readBuffer = readBuffer;
    }

    public String read(SocketChannel socketChannel) throws IOException {
        socketChannel.read(readBuffer);
        readBuffer.clear();
        return new String(readBuffer.array()).trim();
    }
}
