package nioserver.generators;

import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public interface ServerGenerator {
    ServerSocketChannel generateServerChannel();
    Selector generateSelector();
}
