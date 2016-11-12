package ro.ucv.ace.dijkstra.parallel;


import ro.ucv.ace.minheap.VertexMinHeap;
import ro.ucv.ace.graph.model.Edge;
import ro.ucv.ace.graph.model.Graph;
import ro.ucv.ace.graph.model.Vertex;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Geo on 09.11.2016.
 */
public class DijkstraThread implements Runnable {

    private SyncQueue<Edge> shared;

    private AtomicBoolean done;

    private Map<Vertex, Vertex> predecessors;

    private Graph graph;

    private VertexMinHeap weightMinQueue;

    public DijkstraThread(Graph graph, SyncQueue<Edge> shared, AtomicBoolean done,
                          Map<Vertex, Vertex> predecessors, VertexMinHeap weightMinQueue) {
        this.shared = shared;
        this.done = done;
        this.predecessors = predecessors;
        this.weightMinQueue = weightMinQueue;
        this.graph = graph;
    }

    @Override
    public void run() {
        while (!done.get()) {
            Edge e = shared.poll();
            if (e == null) {
                break;
            }
            Vertex u = e.getVertex1();
            Vertex v = e.getVertex2();

            Double sum = u.getDistanceToSource() + graph.distanceBetween(u, v);
            if (v.getDistanceToSource() > sum) {
                weightMinQueue.updateDistance(v, sum);
                predecessors.replace(v, u);
            }
        }
    }

}
