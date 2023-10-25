import com.mongodb.client.MongoDatabase;
import com.sun.net.httpserver.HttpServer;
import dto.UserDto;
import dto.Utility;
import model.UserRecord;
import org.bson.Document;
import rest.RootHttpHandler;
import rest.UserHttpHandler;
import util.Utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Demo {
   public static void main(String[] args) throws IOException {
       AppInitializer appInit = new AppInitializer();
       appInit.init();
       MongoDatabase db = appInit.getMongoDatabase();
       UserDto userDto = new UserDto(db);
       Utility dtoUtility = new Utility();
       httpserver.Utility httpUtility = new httpserver.Utility();
       userDto.deleteAll();
       UserRecord user = userDto.create(new UserRecord("Vlas", "Dielov"));
       System.out.println("One of user: " + user.toJSON());
       user.setFirstName("Vlass");
       System.out.println("User id before replaceOne: " + user.getId());
       user = userDto.replace(user);
       user = userDto.get(user.getId());
       System.out.println("One of user after replaceOne: " + user.toJSON());
       Document upd = new Document().append("firstName", "Vlas");
       userDto.update(user.getId(), new Document("$set", upd));
       user = userDto.get(user.getId());
       System.out.println("One of user after update: " + user.toJSON());
       HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);



//       public static void main(String[] args) {
//           MongoClient client = new MongoClient();
//           MongoDatabase db = client.getDatabase("testdb");
//           MongoCollection<Document> collection = db.getCollection("user");
//           printCollection(collection);
//
//           Document userDoc = new Document().append("firstName", "Vlas").append("secondName", "Dielov");
//           System.out.println(userDoc);
//           collection.insertOne(userDoc);
//           System.out.println(userDoc);
//
//           Document userCheck = collection.find(Filters.eq("_id", new ObjectId(userDoc.getString("_id")))).first();
//       }
//
//       public static void printCollection(MongoCollection<Document> collection) {
//           StreamSupport.stream(collection.find().spliterator(), false).toList().forEach(System.out::println);
//       }
//       public UserRecord create(UserRecord user) {
//           Document doc = user.toDocument();
//           this.userCollection.insertOne(doc);
//           String id = doc.getObjectId("_id").toHexString();
//           user.setId(id);
//           return user;
//       }
//
//       public UserRecord replace(UserRecord user) {
//           Document doc = user.toDocument();
//           // mongodb does not like to change object id
//           UpdateResult res = this.userCollection.replaceOne(new Document("_id", new ObjectId(user.getId())), doc);
//           return res.wasAcknowledged() ? user : null;
//       }
//
//       public boolean update(String id, Document doc) {
//           // mongodb does not like to change object id
//           return this.userCollection.updateOne(new Document("_id", new ObjectId(id)), doc).wasAcknowledged();
//       }
//
//       public void deleteAll() {
//           this.userCollection.deleteMany(new Document());
//       }

       // real 4 threads will try to handle load
       server.setExecutor(Executors.newFixedThreadPool(4));
//       server.setExecutor(new Executor() {
//           @Override
//           public void execute(Runnable command) {
//               // this is executed in separate http dispatcher thread
//               // each request is process in queue by this thread
//               try {
//                   Thread.sleep(1000);
//               } catch (InterruptedException e) {
//                   throw new RuntimeException(e);
//               }
//               System.out.println("Current thread name: " + Thread.currentThread().getName());
//               command.run();
//           }
//       });
       server.createContext("/", new RootHttpHandler(new Utils(), httpUtility));
       server.createContext("/api/users", new UserHttpHandler(userDto, dtoUtility, httpUtility));
       server.start();
//       Thread.sleep(10000);
   }
}
