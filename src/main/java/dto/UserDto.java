package dto;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.result.UpdateResult;
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
        IndexOptions opts = new IndexOptions();
        opts.unique(true);
        userCollection.createIndex(new Document("secondName", 1), opts);
    }

    public List<UserRecord> getAll() {
        return StreamSupport
                .stream(this.userCollection.find().spliterator(), false)
                .map(UserRecord::new)
                .collect(Collectors.toList());
    }

    public UserRecord get(String id) {
        Document user = userCollection.find(Filters.eq("_id", new ObjectId(id))).first();
        if (user == null) return null;
        return new UserRecord(user);
    }

    public UserRecord create(UserRecord user) {
        Document doc = user.toDocument();
        this.userCollection.insertOne(doc);
        String id = doc.getObjectId("_id").toHexString();
        user.setId(id);
        return user;
    }

    public UserRecord replace(UserRecord user) {
        Document doc = user.toDocument();
        // mongodb does not like to change object id
        UpdateResult res = this.userCollection.replaceOne(new Document("_id", new ObjectId(user.getId())), doc);
        return res.wasAcknowledged() ? user : null;
    }

    public boolean update(String id, Document doc) {
        // mongodb does not like to change object id
        return this.userCollection.updateOne(new Document("_id", new ObjectId(id)), doc).wasAcknowledged();
    }

    public void deleteAll() {
        this.userCollection.deleteMany(new Document());
    }
}
