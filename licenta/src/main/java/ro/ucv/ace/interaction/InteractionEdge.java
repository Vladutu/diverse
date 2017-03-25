package ro.ucv.ace.interaction;

/**
 * Created by Geo on 25.03.2017.
 */
public class InteractionEdge {

    private String fromId;

    private String toId;

    public InteractionEdge(String fromId, String toId) {
        this.fromId = fromId;
        this.toId = toId;
    }

    public String getFromId() {
        return fromId;
    }

    public String getToId() {
        return toId;
    }
}
