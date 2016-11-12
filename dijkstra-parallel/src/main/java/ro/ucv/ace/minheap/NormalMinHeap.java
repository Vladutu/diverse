package ro.ucv.ace.minheap;


import ro.ucv.ace.graph.model.Vertex;

import java.util.PriorityQueue;

/**
 * Created by Geo on 09.11.2016.
 */
public class NormalMinHeap implements VertexMinHeap {

    private PriorityQueue<Vertex> priorityQueue;

    public NormalMinHeap() {
        priorityQueue = new PriorityQueue<>((v1, v2) -> v1.getDistanceToSource().compareTo(v2.getDistanceToSource()));
    }


    @Override
    public void add(Vertex vertex) {
        priorityQueue.add(vertex);
    }

    @Override
    public Vertex poll() {
        return priorityQueue.poll();
    }

    @Override
    public boolean isEmpty() {
        return priorityQueue.isEmpty();
    }

    @Override
    public void updateDistance(Vertex vertex, Double value) {
        priorityQueue.remove(vertex);
        vertex.setDistanceToSource(value);
        priorityQueue.add(vertex);
    }
}
