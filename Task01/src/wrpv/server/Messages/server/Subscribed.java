package wrpv.server.Messages.server;

import wrpv.server.Messages.Message;

public class Subscribed extends Message {


    private static final long serialVersionUID = 102L;

    public String topicName;

    public Subscribed(String groupName) {
        this.topicName = groupName;
    }

    public String toString(){
        return String.format("Subscribed(%s)", topicName);
    }

}
