package ro.ucv.ace.interaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Geo on 25.03.2017.
 */
public class ExportAuthorGraph {

    public List<AuthorNode> nodes = new ArrayList<>();

    public List<InteractionEdge> edges = new ArrayList<>();

    public void addNode(AuthorNode node) {
        nodes.add(node);
    }

    public void addEdge(InteractionEdge edge) {
        edges.add(edge);
    }
}
