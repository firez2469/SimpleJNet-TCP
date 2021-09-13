# SimpleJNet-TCP
A TCP networking library for Java.
This library simplifies the creation of a TCP Listener/Server and Client.
Go to [releases](https://github.com/firez2469/SimpleJNet-TCP/releases/) to download the latest version!

Check out the [documentation](https://firez2469.github.io/SimpleJNet-TCP/) for more details:

SimpleJNet-TCP allows for the creation of a TCP Server which can:
- Wait for Clients to connect
- Recieve String messages from Clients
- Send String messages to individual Clients

It also allows for the creation of a TCP Client which can:
- Connect to a Server
- Send Messages to a Server
- Recieve Messages from the Server it is connected to.

TCP SERVER
-----------
1. Start by instancing a new Server object with port 6777.

<code>Server s = new Server(6777);</code>

2. The server is initialized. If a client has connected you can send it data by using the <code> send()</code> method.

<code> s.send("Hello client",0)</code>

The Send method takes in two variables: The message, and the index for the client you want to send the message to.
When a client connects, the server adds it to a list of connected clients. This allows you to specify what client you want to send a message to.
If you want to broadcast a message to all clients you can do this:

<pre>
<code>for(int i = 0; i < s.connections.size(); i++) {
  s.send("Hello client "+i.toString(),i);
}</code></pre>

3. If you want to disconnect use the .close() method

<code> s.close()</code>

4. If you want to read incoming messages, use the hasMessage() and getNextMessage() methods to read it from the buffer:

<pre>
<code>while(s.hasMessage()){
  String message = s.getNextMessage();
}</code>
</pre>

TCP CLIENT
-----------

1. To create a new TCP client initialize the <code>Client</code> Object:

<code>Client c = new Client();</code>

2. To connect to a server use the connect() method. This method takes in two variables. The IP and port::

<code> c.connect("127.0.0.1",6777");</code>

3. To send a message to the server use the 
send() method. The method takes in one variable which is the message.

<code>c.send("Hello server!");</code>

4. To read all incoming messsages use the hasMessage() and getNextMessage() methods to read from the buffer:

<pre>
<code>while(c.hasMessage()){
  String message = c.getNetMessage();
}</code>
</pre>

5. To disconnect from the server use the .disconnect() method

<code>c.disconnect()</code>





