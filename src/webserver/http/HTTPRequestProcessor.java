package webserver.http;

import disk.NIOFileReader;
import webserver.server.Router;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class HTTPRequestProcessor {

    private String basePath;

    private String fileName = null;
    private String fileData = null;

    public HTTPRequestProcessor(String basePath) {
        this.basePath = basePath;
    }

    public boolean processRequest(HTTPMessage httpRequest) {
        Router router = new Router(httpRequest, basePath);
        File requestedFile = router.route();

        try {
            RandomAccessFile file = new RandomAccessFile(requestedFile, "r");
            fileData = new NIOFileReader(file).readString(ByteBuffer.allocate((int) file.length()));
            fileName = requestedFile.getName();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public String fileName() {
        return fileName;
    }

    public String fileData() {
        return fileData;
    }
}
