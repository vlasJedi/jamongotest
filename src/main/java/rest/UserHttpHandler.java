package rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dto.UserDto;
import dto.Utility;
import model.SerialasableToJSON;

import java.io.IOException;

public class UserHttpHandler implements HttpHandler {
    final UserDto userDto;
    final Utility utility;
    final httpserver.Utility httpUtility;
    public UserHttpHandler(UserDto userDto, Utility utility, httpserver.Utility httpUtility) {
        this.userDto = userDto;
        this.utility = utility;
        this.httpUtility = httpUtility;
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
       String toReturn = this.utility.serializeListToJsonArray(
               this.userDto.getAll().stream().map((item) -> (SerialasableToJSON) item).toList());
       try {
           this.httpUtility.finishWithSuccess(toReturn, exchange);
       } catch (IOException e) {
           this.httpUtility.finishWithServerError("Could not write a response", exchange);
       }
    }
}
