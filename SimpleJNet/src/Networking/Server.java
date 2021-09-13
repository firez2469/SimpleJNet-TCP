package Networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Class handles all Networking Server related operations on the network. Ie: It runs the Server.
 */
public class Server extends Thread{
  private int port;
  private ServerSocket socket;
  public List<Socket> connections = new ArrayList<>();
  private List<String> receivedMessages = new ArrayList<String>();

  /**
   * Takes in the port which the server listens on.
   * @param port  The port # for the server.
   * @throws IOException
   */
  public Server(int port) throws IOException {
    this.port=port;
    socket = new ServerSocket(port);
    ServerListener listener1 = new ServerListener(this.port,this.socket,connections,receivedMessages);
    Thread thread= new Thread(listener1);
    thread.start();

  }

  //modify to allow for a specific connection

  /**
   * Sends a string message to the given user in the connections List field.
   * @param message The message being sent.
   * @param user The index of the user in the connections list field
   * @throws IOException
   */
  public void send(String message,int user) throws IOException {
    Socket conn = connections.get(user);
    OutputStream out = conn.getOutputStream();
    PrintWriter writer = new PrintWriter(out);
    writer.println(message);
    writer.flush();
  }

  /**
   * Closes the server and all its connections
   * @throws IOException
   */
  public void close() throws IOException {
    socket.close();
  }

  /**
   * Checks whether the Networking Server Buffer has a message from a Networking Client.
   * @return  True if the buffer has a message.
   */
  public boolean hasMessage(){
    return this.receivedMessages.size()>0;
  }

  /**
   * Gets the next message in the buffer.
   * @return  The next String message in the buffer.
   */
  public String getNextMessage(){
    String msg = this.receivedMessages.get(0);
    this.receivedMessages.remove(0);
    return msg;
  }


}

class ServerListener extends Thread{
  int port;
  ServerSocket serverSocket;
  List<Socket> connections;
  List<String> received;
  public ServerListener(int port,ServerSocket serverSocket, List<Socket> connections,List<String> received){
    this.port = port;
    this.serverSocket = serverSocket;
    this.connections = connections;
    this.received = received;
  }

  public void run(){
    try{
      System.out.println("Waiting on new connection...");
      Socket socket = serverSocket.accept();
      connections.add(socket);
      ServerListener newListener = new ServerListener(this.port,this.serverSocket,this.connections,this.received);
      Thread lThread = new Thread(newListener);
      lThread.start();

      //connection established established
      System.out.println("Connection established with"+socket.getInetAddress().getHostAddress().toString());
      InputStream input = socket.getInputStream();

      InputStreamReader iR = new InputStreamReader(input);
      BufferedReader reader = new BufferedReader(iR);
      while(true) {
        Object rData = reader.readLine();
        if(rData!=null){
          this.received.add(rData.toString());

        }

      }

    }catch(IOException e){
      System.out.println("Waiting on connection...");
    }
  }
}