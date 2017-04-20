package webserver.tests;

import org.junit.Assert;
import org.junit.Test;
import webserver.http.HTTPMessage;

public class HTTPMessageTests {

    private String getRequest = "GET /pages/index.html?test=data HTTP/1.1\r\n" +
            "Host: localhost:1337\r\n" +
            "Connection: keep-alive\r\n" +
            "Cache-Control: max-age=0\r\n" +
            "Upgrade-Insecure-Requests: 1\r\n" +
            "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36\r\n" +
            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\r\n" +
            "Accept-Encoding: gzip, deflate, sdch, br\r\n" +
            "Accept-Language: en-US,en;q=0.8\r\n" +
            "Cookie: Idea-7e056c6a=6976da8f-0869-4e15-95ca-6f8c26d0c340\r\n";

    private String postRequest = "POST /pages/index.html HTTP/1.1\r\n" +
            "Host: localhost:1337\r\n" +
            "Connection: keep-alive\r\n" +
            "Content-Length: 9\r\n" +
            "Accept: */*\r\n" +
            "Origin: http://localhost:1337\r\n" +
            "X-Requested-With: XMLHttpRequest\r\n" +
            "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36\r\n" +
            "Content-Type: application/x-www-form-urlencoded; charset=UTF-8\r\n" +
            "Referer: http://localhost:1337/index.html\r\n" +
            "Accept-Encoding: gzip, deflate, br\r\n" +
            "Accept-Language: en-US,en;q=0.8\r\n" +
            "Cookie: Idea-7e056c6a=6976da8f-0869-4e15-95ca-6f8c26d0c340\r\n" +
            "\r\n" +
            "test=data";

    @Test
    public void testGetHttpRequest() {
        System.out.println("---------------- TESTING GET HTTP REQUEST ----------------");
        HTTPMessage httpReq = new HTTPMessage(getRequest);
        checkTests(httpReq);
        Assert.assertEquals("GET /pages/index.html?test=data HTTP/1.1", httpReq.request());
        Assert.assertEquals("GET", httpReq.requestType());
    }

    @Test
    public void testPostHttpRequest() {
        System.out.println("---------------- TESTING POST HTTP REQUEST ----------------");
        HTTPMessage httpReq = new HTTPMessage(postRequest);
        checkTests(new HTTPMessage(postRequest));
        Assert.assertEquals("POST /pages/index.html HTTP/1.1", httpReq.request());
        Assert.assertEquals("POST", httpReq.requestType());
    }

    private void checkTests(HTTPMessage httpReq) {
        Assert.assertEquals("Idea-7e056c6a=6976da8f-0869-4e15-95ca-6f8c26d0c340", httpReq.fieldValue("Cookie"));
        Assert.assertEquals("localhost:1337", httpReq.fieldValue("Host"));
        Assert.assertEquals("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36", httpReq.fieldValue("User-Agent"));
        Assert.assertNull(httpReq.fieldValue("Should-Be-Null"));
        Assert.assertEquals("HTTP/1.1", httpReq.requestProtocol());
        Assert.assertEquals("/pages/index.html", httpReq.requestPath());
        Assert.assertEquals("test=data", httpReq.data());
    }
}
