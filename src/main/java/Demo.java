import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.List;

public class Demo {
   public static void main(String[] args) {
       AppInitializer appInit = new AppInitializer();
       appInit.init();
       MongoDatabase db = appInit.getMongoDatabase();
       UserDto userDto = new UserDto(db);
       List<Document> users = userDto.getAll();
       System.out.println("Users count is: " + users.size());
       System.out.println("Hello");
   }
}
