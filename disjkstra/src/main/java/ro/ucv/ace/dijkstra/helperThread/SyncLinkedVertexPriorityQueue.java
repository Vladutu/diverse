package ro.ucv.ace.dijkstra.helperThread;

import ro.ucv.ace.graph.Vertex;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Geo on 11.11.2016.
 */
public class SyncLinkedVertexPriorityQueue implements VertexPriorityQueue {

    private List<Vertex> list;

    public SyncLinkedVertexPriorityQueue() {
        list = new LinkedList<>();
    }

    @Override
    public void add(Vertex vertex) {
        int index = 0;

        for (Vertex v : list) {
            if (v.compareTo(v) >= 0) {
                break;
            }

            index++;
        }

        list.add(index, vertex);
    }

    @Override
    public Vertex poll() {
        return list.remove(0);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void updateDistance(Vertex vertex, Double value) {
        list.remove(vertex);
        vertex.setDistanceToSource(value);
        add(vertex);
    }

    @Override
    public Vertex get(int index) {
        if (index >= list.size()) {
            return null;
        }

        return list.get(index);
    }
}
