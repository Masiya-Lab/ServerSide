package wrpv.server.Messages.client;

import wrpv.server.Broker;
import wrpv.server.Client;
import wrpv.server.Messages.Message;

public class Unsubscribe extends Message<Client> {
    private static final long serialVersionUID = 2L;

    public String topicName;

    public Unsubscribe(String topicName) {
        this.topicName = topicName;
    }
    @Override
    public String toString() {
        return String.format("Unsubscribe('%s')", topicName);
    }

    @Override
    public void apply(Client client) {
        // Client leaves the group they are currently in.
        Broker.unsubscribe(client);
    }

}
