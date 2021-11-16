package wrpv.server;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class BrokerServer {
   private ServerSocket server;

   private int clientID=0;

   public BrokerServer()throws Exception{
       //Start new server socket on port 5050
       server = new ServerSocket(5050);
       System.out.printf("BrokerServer started on: %s :5050\n", InetAddress.getLocalHost().getHostAddress());

       //Initial topic that everyone can subscribe to
       Broker.addTopic("Squid");

       while (true){
           Socket client = server.accept();
//increment client Id
           clientID++;

           System.out.printf("Connection request received: %s :%s\n",client.getInetAddress().getHostAddress(),clientID);


           //create  new client connection object to manage
           Client pub_subClient = new Client(client,clientID);
       }


   }
}
