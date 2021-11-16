package wrpv.server.Messages.server;

import wrpv.server.Messages.Message;

import java.util.Date;

public class PublishMessageReceived extends Message {
    private static final long serialVersionUID = 100L;
    public Date timeStamp;
    public String groupName;
    public String chatMessage;

    public PublishMessageReceived(String groupName, String chatMessage) {
        this.groupName = groupName;
        this.chatMessage = chatMessage;
        timeStamp = new Date();
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
