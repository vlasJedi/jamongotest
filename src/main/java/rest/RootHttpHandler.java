package rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RootHttpHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        final byte[] text = "<div style=\"text-align: center\">This is mongoDB test playground</div>".getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(200, text.length);
        exchange.getResponseBody().write(text);
        exchange.close();
    }
}
