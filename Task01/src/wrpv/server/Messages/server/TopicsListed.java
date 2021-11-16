package wrpv.server.Messages.server;

import wrpv.server.Broker;
import wrpv.server.Messages.Message;

import java.util.ArrayList;
import java.util.List;

public class TopicsListed extends Message {

    private static final long serialVersionUID = 101L;

    public List<String> topicNames;

    public TopicsListed(){
        topicNames = new ArrayList<>(Broker.subscribers.keySet());
    }

    @Override
    public String toString() {
        return String.format("TopicsListed (%s)",topicNames.toString());
    }

}
