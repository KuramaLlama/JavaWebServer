package webserver.http;

import nioserver.channelio.OnWrite;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class HTTPResponse implements OnWrite {

    private HTTPRequestProcessor httpProcessor;

    public HTTPResponse(HTTPRequestProcessor httpProcessor) {
        this.httpProcessor = httpProcessor;
    }

    @Override
    public void write(SocketChannel socketChannel, String callback) throws IOException {
        ByteBuffer writeBuffer = ByteBuffer.wrap(
                ((httpProcessor.processRequest(new HTTPMessage(callback))) ?
                        buildOkResponse() : buildNotFoundResponse()).getBytes());
        socketChannel.write(writeBuffer);
        socketChannel.close();
    }

    private String buildOkResponse() {
        HTTPStringBuilder httpBuilder = new HTTPStringBuilder("HTTP/1.1 200 OK");
        httpBuilder.addHeader("Content-Type", "text/"+getContentType());
        httpBuilder.addHeader("Content-Length", Integer.toString(httpProcessor.fileData().length()));
        httpBuilder.addHeader("Connection", "keep-alive");
        httpBuilder.body(httpProcessor.fileData());
        return httpBuilder.toString();
    }

    private String buildNotFoundResponse() {
        String notFoundMsg = "<h1>Oops! File not found!</h1>";
        HTTPStringBuilder httpBuilder = new HTTPStringBuilder("HTTP/1.1 404 Not Found");
        httpBuilder.addHeader("Content-Type", "text/html");
        httpBuilder.addHeader("Content-Length", Integer.toString(notFoundMsg.length()));
        httpBuilder.addHeader("Connection", "keep-alive");
        httpBuilder.body(notFoundMsg);
        return httpBuilder.toString();
    }

    private String getContentType() {
        String fileName = httpProcessor.fileName();
        return fileName.substring(fileName.indexOf('.')+1);
    }
}

