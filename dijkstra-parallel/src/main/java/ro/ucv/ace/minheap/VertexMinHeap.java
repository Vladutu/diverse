package ro.ucv.ace.minheap;


import ro.ucv.ace.graph.model.Vertex;

/**
 * Created by Geo on 09.11.2016.
 */
public interface VertexMinHeap {

    void add(Vertex vertex);

    Vertex poll();

    boolean isEmpty();

    void updateDistance(Vertex vertex, Double value);
}
