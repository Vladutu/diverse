package ro.ucv.ace.interaction;

/**
 * Created by Geo on 25.03.2017.
 */
public class AuthorNode {

    private String id;

    private String label;

    public AuthorNode(String id, String label) {
        this.id = id;
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }
}
