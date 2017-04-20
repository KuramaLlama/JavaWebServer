package webserver.server;

import webserver.http.HTTPMessage;

import java.io.File;

public class Router {

    private HTTPMessage httpMessage;
    private String basePath;

    public Router(HTTPMessage httpMessage, String basePath) {
        this.httpMessage = httpMessage;
        this.basePath = basePath;
    }

    public File route() {
        createDirectory();
        return new File(basePath + httpMessage.requestPath());
    }

    private void createDirectory() {
        File directory = new File(basePath);
        if(!directory.exists())
            directory.mkdir();
    }
}
