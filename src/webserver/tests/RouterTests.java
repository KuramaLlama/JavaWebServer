package webserver.tests;

import org.junit.Assert;
import org.junit.Test;
import webserver.http.HTTPMessage;
import webserver.server.Router;

import java.io.File;


public class RouterTests {

    @Test
    public void testRouter() {
        System.out.println("---------------- TESTING HTTP ROUTER ----------------");
        HTTPMessage mockHTTPMsg = new HTTPMessage("GET /test.html HTTP/1.1\r\n" +
                "Host: localhost:1337\r\n" +
                "Connection: keep-alive\r\n" +
                "Cache-Control: max-age=0\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\r\n" +
                "Accept-Encoding: gzip, deflate, sdch, br\r\n" +
                "Accept-Language: en-US,en;q=0.8\r\n" +
                "Cookie: Idea-7e056c6a=6976da8f-0869-4e15-95ca-6f8c26d0c340\r\n");
        Router router = new Router(mockHTTPMsg, System.getProperty("user.dir") + "/src/webserver/tests/testresources/");
        File requestedFile = router.route();
        System.out.println("Requested File Path: " + requestedFile);
        System.out.println("Requested File Exists: " + requestedFile.exists());
        Assert.assertTrue(requestedFile.exists());
    }
}
