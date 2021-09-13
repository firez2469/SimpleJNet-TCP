package Networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Class handles all Networking Client related operations on the network. Ie: It runs the client.
 */
public class Client {
  String ip;
  int port;
  Socket socket;
  private List<String> receivedMessages = new ArrayList<String>();
  public Client() throws IOException {

  }

  /**
   * Connects to the given ip and port.
   * @param ip  The IP Address of the Networking.Server.
   * @param port  The Port of the Networking.Server.
   */
  public void connect(String ip,int port){
    this.ip = ip;
    this.port = port;
    try {
      socket = new Socket(ip,port);
      System.out.println("Connected with "+ip+":"+port);
      ClientListener listener = new ClientListener(socket,this.receivedMessages);
      Thread listenerThread = new Thread(listener);
      listenerThread.start();

    } catch (IOException e) {
      System.out.println("Creating new connection....");
    }
  }

  /**
   * Sends a message to the server.
   * @param message The string message being sent.
   * @throws IOException
   */
  public void send(String message) throws IOException {
    OutputStream stream = socket.getOutputStream();
    PrintWriter writer = new PrintWriter(stream);
    writer.println(message);
    writer.flush();
  }

  /**
   * Disconnects from the server.
   * @throws IOException
   */
  public void disconnect() throws IOException {
    socket.close();
    System.out.println("Disconnected");
  }

  /**
   * Checks whether the Networking Client has a message in the buffer.
   * @return  True if the buffer has a message.
   */
  public boolean hasMessage(){
    return this.receivedMessages.size()>0;
  }

  /**
   * Gets the next message in the buffer.
   * @return    The next String message in the buffer.
   */
  public String getNextMessage(){
    String msg = this.receivedMessages.get(0);
    this.receivedMessages.remove(0);
    return msg;
  }

}


class ClientListener extends Thread{
  Socket socket;
  List<String> received;
  public ClientListener(Socket socket,List<String> received){
    this.socket = socket;
    this.received = received;
  }

  public void run(){
    System.out.println("Networking.Client Listening on "+socket.getPort());
    try{
      InputStream input = socket.getInputStream();

      InputStreamReader iR = new InputStreamReader(input);
      BufferedReader reader = new BufferedReader(iR);
      while(true) {
        Object rData = reader.readLine();
        if(rData!=null){
          received.add(rData.toString());

        }

      }

    }catch(IOException e){

    }
  }

}