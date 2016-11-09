package ro.ucv.ace.dijkstra.parallelv2;

import ro.ucv.ace.graph.Vertex;

/**
 * Created by Geo on 09.11.2016.
 */
public class VertexWrapper {

    private Vertex vertex;

    public VertexWrapper() {
        this.vertex = new Vertex(-1);
    }

    public Vertex getVertex() {
        return vertex;
    }

    public void setVertex(Vertex vertex) {
        this.vertex = vertex;
    }
}
