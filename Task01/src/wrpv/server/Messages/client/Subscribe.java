package wrpv.server.Messages.client;

import wrpv.server.Broker;
import wrpv.server.Client;
import wrpv.server.Messages.Message;

public class Subscribe extends Message<Client> {
    public static final long serialVersionUID =1L;

    public String topicName;

    public Subscribe(String topicName) {
        this.topicName = topicName;
    }

    @Override
    public void apply(Client Client) {
        Broker.subscribe(topicName,Client);
    }

    @Override
    public String toString() {
        return String.format("Subscribe('%s')", topicName);
    }

}
