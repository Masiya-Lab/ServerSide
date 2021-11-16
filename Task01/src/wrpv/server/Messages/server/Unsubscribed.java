package wrpv.server.Messages.server;

import wrpv.server.Messages.Message;

public class Unsubscribed extends Message {
    private static final long serialVersionUID = 103L;

    public String topicName;

    public Unsubscribed(String topicName) {
        this.topicName = topicName;
    }

    public String toString(){
        return String.format("Unsubscribed('%s')", topicName);
    }

}
