package wrpv.server;

import wrpv.server.Messages.Message;

@FunctionalInterface
public interface Subscriber {
    void onPublished(Message message);
}
