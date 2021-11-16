package wrpv.server.Messages.client;

import wrpv.server.Broker;
import wrpv.server.Client;
import wrpv.server.Messages.Message;

public class Quit extends Message<Client> {

    private static final long serialVersionUID = 4L;

    @Override
    public String toString() {
        return "Quit()";
    }

    @Override
    public void apply(Client client) {
        Broker.unsubscribe(client);
    }

}
