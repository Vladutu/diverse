package ro.ucv.ace.dijkstra.helperThread;

import ro.ucv.ace.graph.Graph;
import ro.ucv.ace.graph.Vertex;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;

/**
 * Created by Geo on 11.11.2016.
 */
public class EdgeRelaxationHelper implements Runnable {

    private int id;

    private Graph graph;

    private Map<Vertex, Vertex> predecessors;

    private VertexPriorityQueue weightMinQueue;

    private Lock lock;

    private AtomicBoolean done;

    private AtomicBoolean stop;

    public EdgeRelaxationHelper(int id, Graph graph, Map<Vertex, Vertex> predecessors, VertexPriorityQueue weightMinQueue,
                                Lock lock, AtomicBoolean done, AtomicBoolean stop) {
        this.id = id;
        this.graph = graph;
        this.predecessors = predecessors;
        this.weightMinQueue = weightMinQueue;
        this.lock = lock;
        this.done = done;
        this.stop = stop;
    }

    @Override
    public void run() {
        while (!weightMinQueue.isEmpty()) {
            while (!done.get()) {
                stop.set(false);
                Vertex vertex = weightMinQueue.get(id);

                if (vertex != null && vertex.getDistanceToSource() != Double.POSITIVE_INFINITY) {
                    Set<Vertex> adjacent = graph.getAdjacentVertices(vertex);

                    for (Vertex y : adjacent) {
                        if (!stop.get()) {
                            lock.lock();
                            try {
                                if (!done.get()) {
                                    relaxEdges(vertex, y);
                                } else {
                                    stop.set(true);
                                }
                            } finally {
                                lock.unlock();

                            }
                        }
                    }
                }
            }
        }
    }

    private void relaxEdges(Vertex x, Vertex y) {
        Double sum = x.getDistanceToSource() + graph.distanceBetween(x, y);

        if (y.getDistanceToSource() > sum) {
            weightMinQueue.updateDistance(y, sum);
            predecessors.replace(y, x);
        }
    }


}
