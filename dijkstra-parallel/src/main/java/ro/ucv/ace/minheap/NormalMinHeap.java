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
        Vertex theOne = priorityQueue.stream()
                .filter(v -> v.equals(vertex))
                .findFirst()
                .get();

        priorityQueue.remove(theOne);
        theOne.setDistanceToSource(value);
        priorityQueue.add(theOne);
    }

    @Override
    public int size() {
        return priorityQueue.size();
    }

    public boolean contains(Vertex vertex) {
        return priorityQueue.contains(vertex);
    }

    @Override
    public String toString() {
        return "NormalMinHeap{" +
                "priorityQueue=" + priorityQueue +
                '}';
    }
}
