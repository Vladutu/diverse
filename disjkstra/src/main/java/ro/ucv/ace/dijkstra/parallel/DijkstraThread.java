package ro.ucv.ace.dijkstra.parallel;

import ro.ucv.ace.graph.Edge;
import ro.ucv.ace.graph.Vertex;
import ro.ucv.ace.minheap.VertexMinHeap;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Geo on 09.11.2016.
 */
public class DijkstraThread implements Runnable {

    private volatile SyncQueue<Edge> shared;

    private volatile AtomicBoolean done;

    private volatile Map<Vertex, Vertex> predecessors;

    private List<Edge> edges;

    private volatile VertexMinHeap weightMinQueue;

    public DijkstraThread(SyncQueue<Edge> shared, AtomicBoolean done,
                          Map<Vertex, Vertex> predecessors, List<Edge> edges, VertexMinHeap weightMinQueue) {
        this.shared = shared;
        this.done = done;
        this.predecessors = predecessors;
        this.edges = edges;
        this.weightMinQueue = weightMinQueue;
    }

    @Override
    public void run() {
        while (!done.get()) {
            Edge e = shared.poll();
            if (e == null) {
                break;
            }
            Vertex u = e.getSource();
            Vertex v = e.getDestination();

            Double sum = u.getDistanceToSource() + distance(u, v);
            if (v.getDistanceToSource() > sum) {
                weightMinQueue.updateDistance(v, sum);
                predecessors.replace(v, u);
            }
        }
    }


    private Double distance(Vertex u, Vertex v) {
        return edges.stream()
                .filter(e -> e.getSource().equals(u) && e.getDestination().equals(v))
                .findFirst()
                .get()
                .getWeight();
    }


}
