package nioserver.tests.mocks;

import nioserver.channelio.OnWrite;
import org.junit.Assert;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


public class MockChannelOnWrite implements OnWrite {

    @Override
    public void write(SocketChannel socketChannel, String callback) throws IOException {
        System.out.println("Callback: " + callback);
        System.out.println("Testing Channel right: " + callback);
        Assert.assertEquals("Testing Channel Writing", callback);
        socketChannel.write(ByteBuffer.wrap("Sending back test".getBytes()));
    }
}
