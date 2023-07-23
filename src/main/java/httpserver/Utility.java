package httpserver;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Utility {
    public void finishWithSuccess(String body, HttpExchange exchange) throws IOException {
        byte[] data = body.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(200, data.length);
        OutputStream out = exchange.getResponseBody();
        out.write(data);
        out.flush();
        out.close();
    }

    public void finishWithSuccessBuffered(InputStream is, HttpExchange exchange) throws IOException {
        OutputStream out = exchange.getResponseBody();
        byte[] buffer = new byte[0xFFF];
        long totalSize = 0;
        // intentionally using chunked transfer
        exchange.sendResponseHeaders(200, 0);
        while(is.available() > 0) {
            int res = is.read(buffer);
            if (res > 0) {
                out.write(buffer);
                System.out.println(new String(buffer, StandardCharsets.UTF_8));
                totalSize += res;
            }
        }
        out.flush();
        out.close();
    }

    public void finishWithServerError(String errorText, HttpExchange exchange) {
        byte[] data = errorText.getBytes(StandardCharsets.UTF_8);
        try {
            exchange.sendResponseHeaders(500, data.length);
            OutputStream out = exchange.getResponseBody();
            out.write(data);
        } catch (IOException ignored) {

        }
    }
}
