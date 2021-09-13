package Examples;

import Networking.Client;
import Networking.Server;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Basic local network test.
 */
public class Network {
  public static void main(String[] args) throws IOException, InterruptedException {
    Server s = new Server(6667);
    try {
      TimeUnit.SECONDS.sleep(1);
      Client c = new Client();
      c.connect("127.0.0.1",6667);
      TimeUnit.SECONDS.sleep(1);
      System.out.println("Sending data...");
      c.send("Hello world!");
      TimeUnit.SECONDS.sleep(1);
      c.send("How's life?!");
      TimeUnit.SECONDS.sleep(1);
      s.send("Good!",0);
      TimeUnit.SECONDS.sleep(1);
      Client c2 = new Client();
      c2.connect("127.0.0.1",6667);
      TimeUnit.SECONDS.sleep(1);
      c2.send("Second person saying: Hello there");
      TimeUnit.SECONDS.sleep(1);

      c.disconnect();


    } catch (InterruptedException e) {

    }
  }


}
