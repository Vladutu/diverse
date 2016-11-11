package ro.ucv.ace.dijkstra.helperThread;

import ro.ucv.ace.graph.Vertex;

/**
 * Created by Geo on 11.11.2016.
 */
public interface VertexPriorityQueue {

    void add(Vertex vertex);

    Vertex poll();

    boolean isEmpty();

    void updateDistance(Vertex vertex, Double value);

    Vertex get(int index);
}
