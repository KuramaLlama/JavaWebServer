package webserver;

import nioserver.acceptors.ServerSocketChannelAcceptor;
import nioserver.channelio.SocketChannelReader;
import nioserver.processors.Processor;
import nioserver.processors.SocketChannelProcessor;
import nioserver.runners.ServerAcceptorRunner;
import nioserver.runners.ServerProcessRunner;
import nioserver.selected.OnReadSelected;
import nioserver.selected.OnWriteSelected;
import nioserver.selected.ProcessSelected;
import webserver.http.HTTPRequestProcessor;
import webserver.http.HTTPResponse;
import webserver.server.WebServerGenerator;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.Queue;

public class WebServer implements Closeable {

    private static final String htdocsPath = System.getProperty("user.dir") + "/htdocs";
    private Queue<SocketChannel> socketChannelQueue = new LinkedList<SocketChannel>();

    private int port;

    private WebServerGenerator serverGenerator;
    private Processor processor;
    private ServerAcceptorRunner serverAcceptorRunner;
    private ServerProcessRunner serverProcessRunner;

    public WebServer(int port) {
        this.port = port;
    }

    public void initialize() throws IOException {
        setup();
        this.serverGenerator = new WebServerGenerator(port);
        initProcessor();
        initRunners();
    }

    public void start() throws IOException {
        checkInitialized();
        System.out.println("Server Started listeners port: " + port);
        new Thread(serverAcceptorRunner).start();
        new Thread(serverProcessRunner).start();

    }

    @Override
    public void close() throws IOException {
        serverAcceptorRunner.stop();
        serverProcessRunner.stop();
        serverGenerator.close();
    }

    private void checkInitialized() {
        if(serverGenerator == null || serverAcceptorRunner == null || serverProcessRunner == null)
            throw new NullPointerException("You must call initialize() before start.");
    }

    private void setup() {
        File directory = new File(htdocsPath);
        if(!directory.exists()) {
            directory.mkdir();
            System.out.println("Server setup complete!\r\n Add HTML/CSS/JS files in htdocs folder and restart to continue!");
            System.exit(0);
        }
    }

    private void initProcessor() throws IOException {
        Selector readSelector = Selector.open(), writeSelector = Selector.open();
        SocketChannelReader reader = new SocketChannelReader(ByteBuffer.allocate(8192));
        HTTPResponse httpResponse = new HTTPResponse(new HTTPRequestProcessor(htdocsPath));
        OnReadSelected onRead = new OnReadSelected(readSelector, writeSelector, reader);
        OnWriteSelected onWrite = new OnWriteSelected(writeSelector, httpResponse);
        this.processor = new SocketChannelProcessor(onRead, onWrite, new ProcessSelected());
    }

    private void initRunners() {
        this.serverAcceptorRunner = new ServerAcceptorRunner(new ServerSocketChannelAcceptor(serverGenerator), socketChannelQueue);
        this.serverProcessRunner = new ServerProcessRunner(processor, socketChannelQueue);
    }
}
