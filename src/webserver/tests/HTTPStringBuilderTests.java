package webserver.tests;

import org.junit.Assert;
import org.junit.Test;
import webserver.http.HTTPStringBuilder;

public class HTTPStringBuilderTests {

    @Test
    public void testHTTPStringBuilder() {
        System.out.println("---------------- TESTING HTTP STRING BUILDER ----------------");
        String bodySample = "<h1>This is a test for the html page body</h1>";
        HTTPStringBuilder httpBuilder = new HTTPStringBuilder("HTTP/1.1 200 OK");
        httpBuilder.addHeader("Content-Type", "text/html");
        httpBuilder.addHeader("Content-Length", Integer.toString(bodySample.getBytes().length));
        httpBuilder.addHeader("Connection", "keep-alive");
        httpBuilder.body(bodySample);

        System.out.println("Http Response:\r\n" + httpBuilder.toString());
        Assert.assertEquals("HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: 46\r\n" +
                "Connection: keep-alive\r\n" +
                "\r\n" +
                "<h1>This is a test for the html page body</h1>", httpBuilder.toString());
    }
}
