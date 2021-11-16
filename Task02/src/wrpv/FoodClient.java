package wrpv;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class FoodClient {
    //client connection
    private Socket client;

    //get I/O streams
    private ObjectInputStream in;

    private ObjectOutputStream out;

    //customer num
    private int customerNum;

    //reading and writing Threads
    private Thread readThread;

    private Thread writeThread;

    public FoodClient(Socket client, int customerNum) {
        this.client = client;

        this.customerNum=customerNum;


    }
}
