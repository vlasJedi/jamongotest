package dto;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.UserRecord;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserDto {

    private final MongoDatabase db;
    private final MongoCollection<Document> userCollection;

    public UserDto(MongoDatabase db) {
        this.db = db;
        this.userCollection = this.db.getCollection("user");
    }

    public List<UserRecord> getAll() {
        return StreamSupport
                .stream(this.userCollection.find().spliterator(), false)
                .map(UserRecord::new)
                .collect(Collectors.toList());
    }

    public UserRecord create(UserRecord user) {
        Document doc = user.toDocument();
        this.userCollection.insertOne(doc);
        String id = doc.getObjectId("_id").toHexString();
        user.setId(id);
        return user;
    }

    public UserRecord update(UserRecord user) {
        Document doc = user.toDocument();
        // mongodb does not like to change object id
        this.userCollection.replaceOne(new Document("_id", new ObjectId(user.getId())), doc);
        return user;
    }

    public void deleteAll() {
        this.userCollection.deleteMany(new Document());
    }
}
