package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class HTTPMessage {

    private static final String LINE_TERMINATOR = "\r\n";

    private Map<String, String> headers = new HashMap<String, String>();

    private String[] request;
    private String requestMessage;
    private String data;

    public HTTPMessage(String requestMessage) {
        this.requestMessage = requestMessage;
        parseHTTPMessage();
    }

    public String request() {
        System.out.println(String.join(" ", request));
        return String.join(" ", request);
    }

    public String requestType() {
        return request[0];
    }

    public String requestPath() {
        String[] data = request[1].split("\\?");
        return (requestType().equals("GET") && data.length > 1) ? data[0] : request[1];
    }

    public String requestProtocol() {
        return request[2];
    }

    public String data() {
        return data;
    }

    public Map<String, String> headers() {
        Map<String, String> headersCopy = new HashMap<String, String>();
        headersCopy.putAll(headers);
        return headersCopy;
    }

    public String fieldValue(String field) {
        return headers.get(field);
    }

    private void parseHTTPMessage() {
        String[] lines = requestMessage.split(LINE_TERMINATOR);
        request = lines[0].split(" ");

        for(int i = 1; i < lines.length; i++) {
            String line = lines[i];
            String[] header = line.split(": ");

            if(header.length > 1)
                headers.put(header[0], header[1]);
        }
        parseData();
    }

    private void parseData() {
        if(requestType().equals("GET") && request[1].split("\\?").length > 1) {
            data = request[1].split("\\?")[1];
        } else if(requestType().equals("POST") && requestMessage.split("\r\n\r\n").length > 1) {
            data = requestMessage.split("\r\n\r\n")[1];
        }
    }
}
