package httpserver;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Utility {
    public static void finishWithSuccess(String body, HttpExchange exchange) throws IOException {
        byte[] data = body.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(200, data.length);
        OutputStream out = exchange.getResponseBody();
        out.write(data);
    }
}
