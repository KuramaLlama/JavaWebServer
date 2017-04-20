package nioserver.tests;

import nioserver.channelio.SocketChannelReader;
import nioserver.selected.OnReadSelected;
import nioserver.selected.OnWriteSelected;
import nioserver.selected.ProcessSelected;
import nioserver.tests.mocks.MockChannelOnWrite;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import nioserver.acceptors.ServerSocketChannelAcceptor;
import webserver.server.WebServerGenerator;
import nioserver.processors.SocketChannelProcessor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.*;

public class ServerTests {

    private WebServerGenerator serverGenerator = new WebServerGenerator(1337);
    private ServerSocketChannelAcceptor serverAcceptor = new ServerSocketChannelAcceptor(serverGenerator);

    private ScheduledThreadPoolExecutor executor;
    private Future<SocketChannel> futureSocketChannel;

    @Before
    public void setUp() {
        serverAcceptor.configureBlocking(true);
        executor = new ScheduledThreadPoolExecutor(1);
        futureSocketChannel = executor.submit(serverAcceptor::accept);
    }

    @After
    public void tearDown() throws ExecutionException, InterruptedException, IOException {
        serverGenerator.close();
        executor.shutdown();
    }

    @Test
    public void testServerAcceptor() throws IOException, ExecutionException, InterruptedException {
        System.out.println("---------------- TESTING SERVER ACCEPTOR ----------------");
        new ServerEnvironment().runTest((acceptedChannel, mockClient) -> {
            System.out.println("Mock Client Connected: " + mockClient.isConnected());
            System.out.println("Mock Server Connected: " + acceptedChannel.isConnected());
            Assert.assertTrue(mockClient.isConnected());
            Assert.assertTrue(acceptedChannel.isConnected());
        });
    }

    @Test
    public void testServerProcess() throws IOException, ExecutionException, InterruptedException {
        System.out.println("---------------- TESTING SERVER PROCESS ----------------");
        Selector readSelector = Selector.open(), writeSelector = Selector.open();

        new ServerEnvironment().runTest((acceptedChannel, mockClient) -> {
            SocketChannelReader reader = new SocketChannelReader(ByteBuffer.allocate(48));
            MockChannelOnWrite writer = new MockChannelOnWrite();
            OnReadSelected onRead = new OnReadSelected(readSelector, writeSelector, reader);
            OnWriteSelected onWrite = new OnWriteSelected(writeSelector, writer);
            SocketChannelProcessor serverProcessor = new SocketChannelProcessor(onRead, onWrite, new ProcessSelected());

            mockClient.write(ByteBuffer.wrap("Testing Channel Writing".getBytes()));

            serverProcessor.process(acceptedChannel);

            ByteBuffer readBuffer = ByteBuffer.allocate(48);
            mockClient.read(readBuffer);
            String response = new String(readBuffer.array()).trim();
            System.out.println("Testing Response: " + response);
            Assert.assertEquals("Sending back test", response);

        });
    }

    private class ServerEnvironment {
        private void runTest(RunChannelTest channelTest) throws IOException, ExecutionException, InterruptedException {
            SocketChannel mockClient = SocketChannel.open();
            mockClient.connect(new InetSocketAddress("localhost", 1337));
            SocketChannel acceptedChannel = futureSocketChannel.get();
            acceptedChannel.configureBlocking(false);
            mockClient.configureBlocking(false);

            channelTest.runSocketChannelTest(acceptedChannel, mockClient);

            acceptedChannel.close();
            mockClient.close();
        }
    }

    interface RunChannelTest {
        void runSocketChannelTest(SocketChannel serverChannel, SocketChannel clientChannel) throws IOException;
    }
}


