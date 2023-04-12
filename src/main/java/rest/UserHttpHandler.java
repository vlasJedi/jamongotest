package rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dto.UserDto;
import org.bson.Document;

import java.io.IOException;
import java.util.List;

public class UserHttpHandler implements HttpHandler {
    final UserDto userDto;
    public UserHttpHandler(UserDto userDto) {
        this.userDto = userDto;
    }
    @Override
    public void handle(HttpExchange exchange) {
        switch (exchange.getRequestMethod()) {
            case "GET":
                get(exchange);
                break;
        }
    }

    public void get(HttpExchange exchange) {
        List<Document> toReturn = this.userDto.getAll();
    }
}
