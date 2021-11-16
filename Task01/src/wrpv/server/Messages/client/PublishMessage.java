package wrpv.server.Messages.client;

import wrpv.server.Broker;
import wrpv.server.Client;
import wrpv.server.Messages.Message;
import wrpv.server.Messages.server.PublishMessageReceived;

public class PublishMessage extends Message<Client> {
    private static final long serialVersionUID = 5L;


    public String publishMessage;


    public PublishMessage(String  publishMessage) {
        this. publishMessage =  publishMessage;
    }

    @Override
    public void apply(Client context) {
        String topicName = context.topicName;

        if(topicName.length()==0) return;

        Broker.publish(topicName,
                new PublishMessageReceived(topicName, publishMessage));
    }

    @Override
    public String toString() {
        return String.format("SendPublishMessage('%s')",publishMessage);
    }


}
