package wrpv.server;

import wrpv.server.Messages.Message;
import wrpv.server.Messages.server.Unsubscribed;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * this class is responsible for managing topics and sending messages
 * to clients who have subscribe to certain topics
 */
public class Broker {
    /**
     * Lock to prevent multiple thread manipulating the topics data
     * while busy with an operation
     */
    private static ReentrantLock lock = new ReentrantLock();

    //keeps track of each topic and the subscribers under each topic
    public static final Map<String, Set<Client>> subscribers = new HashMap<>();

    //Singleton instance of broker

    static private Broker instance =null;

    public Broker() {

    }

    public static Broker getInstance(){
        if(instance==null){
            instance = new Broker();
        }
        return instance;
    }

    public static void subscribe(String topic, Client subscriber){
        //Get set of subscribers listening to topic. if none, create a new set
       lock.lock();
        Set<Client> subscriberSet;
        subscriberSet = subscribers.computeIfAbsent(topic,key->new HashSet<>());

        //Add subscriber to the set
        subscriberSet.add(subscriber);
        lock.unlock();
    }

    public static void unsubscribe(String topic, Client subscriber) {
        lock.lock();
        // Get set of subscribers listening to the topic.
        Set<Client> subscriberSet;
        subscriberSet = subscribers.get(topic);

        // If no-one listening, stop.
        if(subscriberSet == null)
            return;

        // Remove from set.
        subscriberSet.remove(subscriber);

        // Empty set? If so, remove the set.
        if(subscriberSet.size() == 0)
            subscribers.remove(topic);

        lock.unlock();
    }

    public static void unsubscribe(Client subscriber){
        lock.lock();

        List<String> groupIn = subscribers.entrySet()
                .stream()
                .filter(entry->entry.getValue().contains(subscriber))
                .map(entry->entry.getKey())
                .collect(Collectors.toList());

        groupIn.forEach(topicName->{
            Set<Client> group = subscribers.get(topicName);

            group.remove(subscriber);

            //Send messages to other clients in group
           Unsubscribed msg = new Unsubscribed(topicName);
            group.forEach(chatClient-> chatClient.onPublished(msg));
        });

        lock.unlock();
    }

    public static void publish(String topic, Message Message) {

       lock.lock();
        Set<Client> subscriberSet;
        subscriberSet = subscribers.get(topic);

        // If no subscribers for the topic, done!
        if(subscriberSet == null)
            return;

        // Notify all subscribers of the publishing of the message.
        subscriberSet.forEach(
                subscriber -> subscriber.onPublished(Message));
        lock.unlock();
    }

    public static void addTopic(String topicName){
        lock.lock();

        if(subscribers.containsKey(topicName)){
            return;
        }

        subscribers.put(topicName,new HashSet<>());

        lock.unlock();
    }

    public static void publishToAll(Message message){
        lock.lock();

        subscribers.values()
                .stream()
                .flatMap(Collection::stream)
                .distinct()
                .forEach(client -> {
                    client.onPublished(message);
                    System.out.println("SendAll: "+client.clientID);
                });


        lock.unlock();
    }


}
