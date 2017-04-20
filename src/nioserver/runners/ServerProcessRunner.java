package nioserver.runners;

import nioserver.processors.Processor;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.Queue;

public class ServerProcessRunner extends Runner {

    private Processor processor;
    private Queue<SocketChannel> socketChannelQueue;

    public ServerProcessRunner(Processor processor, Queue<SocketChannel> socketChannelQueue) {
        this.processor = processor;
        this.socketChannelQueue = socketChannelQueue;
    }

    @Override
    public void run() {
        while(running) {
            if(!socketChannelQueue.isEmpty()) {
                try {
                    processor.process(socketChannelQueue.poll());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            sleep(60);
        }
    }
}
