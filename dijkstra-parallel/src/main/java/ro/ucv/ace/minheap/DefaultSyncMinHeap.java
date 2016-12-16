package ro.ucv.ace.minheap;


import ro.ucv.ace.graph.model.Vertex;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by Geo on 11.11.2016.
 */
public class DefaultSyncMinHeap implements VertexMinHeap {

    private BlockingQueue<Vertex> queue = new PriorityBlockingQueue<>(1000,
            (v1, v2) -> v1.getDistanceToSource().compareTo(v2.getDistanceToSource()));

    @Override
    public void add(Vertex vertex) {
        queue.add(vertex);
    }

    @Override
    public Vertex poll() {
        return queue.poll();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public void updateDistance(Vertex vertex, Double value) {
        queue.remove(vertex);
        vertex.setDistanceToSource(value);
        add(vertex);
    }

    @Override
    public int size() {
        return queue.size();
    }
}
