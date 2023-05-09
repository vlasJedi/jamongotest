import com.mongodb.client.MongoDatabase;
import com.sun.net.httpserver.HttpServer;
import dto.UserDto;
import dto.Utility;
import model.UserRecord;
import rest.RootHttpHandler;
import rest.UserHttpHandler;

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
       user = userDto.update(user);
       System.out.println("One of user after update: " + user.toJSON());
       HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
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
       server.createContext("/", new RootHttpHandler());
       server.createContext("/users", new UserHttpHandler(userDto, dtoUtility, httpUtility));
       server.start();
//       Thread.sleep(10000);
   }
}
