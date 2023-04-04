import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class AppInitializer {
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }
    public void init() {
        createDbConnector();
    }

    private void createDbConnector() {
        this.mongoClient = new MongoClient();
        this.mongoDatabase = this.mongoClient.getDatabase("app");
    }
}
