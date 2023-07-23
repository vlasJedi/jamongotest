package rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import httpserver.Utility;
import util.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class RootHttpHandler implements HttpHandler {
    private static final byte[] FALLBACK = "<div style=\"text-align: center\">Could not load main home page</div>"
            .getBytes(StandardCharsets.UTF_8);
    private final Utils util;
    private final Utility utility;
    public RootHttpHandler(Utils util, Utility utility) {
        this.util = util;
        this.utility = utility;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        InputStream is = util.getResourceStream("index.html");
        exchange.getResponseHeaders().add("Cache-Control", "no-cache");
        exchange.getResponseHeaders().add("Pragma", "no-cache");
        if (is == null) {
            exchange.sendResponseHeaders(200, FALLBACK.length);
            exchange.getResponseBody().write(FALLBACK);
            exchange.close();
            return;
        }
        utility.finishWithSuccessBuffered(is, exchange);
        exchange.close();
    }
}
