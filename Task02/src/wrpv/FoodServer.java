package wrpv;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class FoodServer {
    private ServerSocket server;

    private int customerNum=0;

    public FoodServer() throws IOException {
        //start new server socket om port 5050
        server = new ServerSocket(5050);

        System.out.printf("Food server started on: %s :5050", InetAddress.getLocalHost().getHostAddress());

        while(true){
            //accept client connection
            Socket client = server.accept();

            System.out.printf("Connection request received: %s",client.getInetAddress().getHostAddress());

            //increment customer number
            customerNum++;

            //Create new client connection
            FoodClient foodClient = new FoodClient(client,customerNum);
        }

    }
}
