package dto;

import model.SerialasableToJSON;

import java.util.List;
import java.util.stream.Collectors;

public class Utility {
    public String serializeListToJsonArray(List<SerialasableToJSON> list) {
        return list.stream().map(SerialasableToJSON::toJSON)
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
