package nioserver.runners;

import nioserver.acceptors.ServerSocketChannelAcceptor;

import java.nio.channels.SocketChannel;
import java.util.Queue;

public class ServerAcceptorRunner extends Runner{

    private ServerSocketChannelAcceptor serverAcceptor;
    private Queue<SocketChannel> socketChannelQueue;

    public ServerAcceptorRunner(ServerSocketChannelAcceptor serverAcceptor, Queue<SocketChannel> socketChannelQueue) {
        this.serverAcceptor = serverAcceptor;
        this.socketChannelQueue = socketChannelQueue;
    }

    @Override
    public void run() {
        while(running) {
            SocketChannel acceptedChannel = serverAcceptor.accept();
            if(acceptedChannel != null)
                socketChannelQueue.add(acceptedChannel);
            sleep(60);
        }
    }
}
