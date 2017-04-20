package webserver.http;

import java.util.LinkedHashMap;
import java.util.Map;

public class HTTPStringBuilder {

    private static final String LINE_TERMINATOR = "\r\n";

    private Map<String, String> headers = new LinkedHashMap<String, String>();

    private String status;
    private String body;

    public HTTPStringBuilder(String status) {
        this.status = status;
    }

    public void addHeader(String field, String value) {
        headers.put(field + ": ", value + LINE_TERMINATOR);
    }

    public void body(String body) {
        this.body = body;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(status + LINE_TERMINATOR);
        headers.forEach((field, val) -> builder.append(field+val));
        builder.append(LINE_TERMINATOR);
        builder.append(body);
        return builder.toString();
    }
}
