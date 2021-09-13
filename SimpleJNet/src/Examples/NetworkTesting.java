package Examples;

import Networking.Client;
import Networking.Server;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;


/**
 * Simple texting program for testing this library.
 */
public class NetworkTesting {


  public NetworkTesting() throws IOException {
  }
  public static void main(String[] args) throws IOException {
    System.out.print("Type:");
    InputStream s = System.in;
    Scanner scanner = new Scanner(s);
    String input = scanner.nextLine();
    if(input.equals("server")){
      Server server = new Server(6777);
      while(scanner.hasNext()){
        String nextLine = scanner.nextLine();
        for(int i = 0;i<server.connections.size();i++){
          server.send(nextLine,i);
        }
        if(nextLine.equals("close")){
          server.close();
          System.out.println("Disconnected");
          return;
        }
      }

    }else if (input.equals("client")){
        Client c = new Client();
        boolean isConnected= false;
        System.out.print("IP:");
        String ip = scanner.nextLine();
        System.out.print("PORT:");
        int port = scanner.nextInt();
        c.connect(ip,port);
        while(scanner.hasNext()){
          String nextLine = scanner.nextLine();
          c.send(nextLine);


          if(nextLine.equals("close")){
            c.disconnect();
          }


        }
    }




  }

}
