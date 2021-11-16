package wrpv.server;

import wrpv.server.Messages.Message;
import wrpv.server.Messages.client.Quit;
import wrpv.server.Messages.server.TopicsListed;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Client implements Subscriber{

    private Socket client;

    //I/O streams for communicating with client
    private ObjectInputStream in;
    private ObjectOutputStream out;

    //using a thread-safe queue to handle multiple threads adding
    //to the queue
    private BlockingQueue<Message> outGoingMessages = new LinkedBlockingQueue<>();

    //reads messages from this specific client
    private  ReadThread readThread;

    //writes messages to this specific client
    private WriteThread writeThread;

    //Details about the current connection's client
    public int clientID;
    public String topicName="";

    public Client(Socket client,int clientID){
        this.client =client;
        this.clientID =clientID;
        //start read loop thread
        readThread = new ReadThread();
        readThread.start();

        //add default topic
        Broker.subscribe("Squid",Client.this);

        onPublished(new TopicsListed());
    }


    @Override
    public void onPublished(Message message) {
        try{
            outGoingMessages.put(message);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }


    private class ReadThread extends Thread{

        @Override
        public void run() {
            try{
                System.out.println(clientID+" :Read thread started");

                out = new ObjectOutputStream(client.getOutputStream());
                out.flush();
                in = new ObjectInputStream(client.getInputStream());

                System.out.println(clientID+" :Obtained I/O streams");

                //start write loop thread. start write thread here ensure
                //that the I/O streams have been initialised correctly BEFORE
                //starting to read and write messages
                writeThread = new WriteThread();
                writeThread.start();

                //read messages from client
                System.out.println(clientID+" :Started Read Loop...");

                Message msg;

                do {
                    msg = (Message) in.readObject();
                    System.out.println(clientID+" --> "+msg);

                    //Process the message.
                    msg.apply(Client.this);

                }while(msg.getClass() != Quit.class);
                //client the connection
                client.close();

            }catch (Exception e){
                System.out.println(clientID+ ": Read.Exception: "+e.getMessage());
                e.printStackTrace();
            }
            finally {
                System.out.println(clientID+": Leaving topics...");
                Broker.unsubscribe(Client.this);
                System.out.println(clientID+" : stopping Write Thread....");
                writeThread.interrupt();

                System.out.println(clientID+": Read thread finished.");
            }
        }
    }


    private class WriteThread extends Thread{

        @Override
        public void run() {
            System.out.println(clientID+" :started Write Loop thread...");

            writeThread = this;

            try{
                while(!isInterrupted()){
                    Message msg = outGoingMessages.take();

                    out.writeObject(msg);
                    out.flush();

                    System.out.println(msg+" --> Client: "+clientID);
                }
            }catch(Exception e){
                System.out.println(clientID+": write.Exception = "+e.getMessage());
                e.printStackTrace();
            }finally {
                writeThread=null;
                System.out.println(clientID+": write thread finished.");
            }
        }
    }
}
