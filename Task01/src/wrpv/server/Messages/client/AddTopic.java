package wrpv.server.Messages.client;

import wrpv.server.Broker;
import wrpv.server.Client;
import wrpv.server.Messages.Message;

public class AddTopic extends Message<Client> {
    private static final long serialVersionUID = 7L;

    // The name of the topic to create.
    public String topicName;

    public AddTopic(String topicName) {
        this.topicName = topicName;
    }

    @Override
    public String toString() {
        return String.format("AddTopic('%s')", topicName);
    }

    @Override
    public void apply(Client chatClient) {
        // Add new chat group.
        Broker.addTopic(topicName);

        // Return the list of group names to all clients.
        Broker.publishToAll(new ListTopics());
    }
}
