package wrpv.server.Messages.client;

import wrpv.server.Client;
import wrpv.server.Messages.Message;
import wrpv.server.Messages.server.TopicsListed;

public class ListTopics extends Message<Client> {
    public static final long serialVersionUID =3L;

    @Override
    public void apply(Client context) {
        context.onPublished(new TopicsListed());
    }

    @Override
    public String toString() {
        return "ListTopics";
    }

}
