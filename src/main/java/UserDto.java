import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.List;
import java.util.stream.StreamSupport;

public class UserDto {

    private final MongoDatabase db;
    private final MongoCollection<Document> userCollection;

    public UserDto(MongoDatabase db) {
        this.db = db;
        this.userCollection = this.db.getCollection("user");
    }

    public List<Document> getAll() {
        return StreamSupport
                .stream(this.userCollection.find().spliterator(), false)
                .toList();
    }
}
