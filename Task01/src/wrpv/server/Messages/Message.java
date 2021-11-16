package wrpv.server.Messages;

import java.io.Serializable;

public abstract class Message<C> implements Serializable {
    private static final long serialVersionUID = 999L;

    /**
     * Apply this message's logic to a specific context.
     * This will only used for message received from a client
     * @param context The context to apply the message logic too
     */
    public void apply(C context){

    }
}
