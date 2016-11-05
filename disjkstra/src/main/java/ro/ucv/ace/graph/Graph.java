package ro.ucv.ace.graph;

import java.util.List;

/**
 * Created by Geo on 05.11.2016.
 */
public class Graph {

    private List<Vertex> vertices;

    private List<Edge> edges;

    public Graph(List<Vertex> vertices, List<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
