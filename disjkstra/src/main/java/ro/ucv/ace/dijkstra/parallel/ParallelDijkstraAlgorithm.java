package ro.ucv.ace.dijkstra.parallel;

import ro.ucv.ace.dijkstra.AbstractDijkstraAlgorithm;
import ro.ucv.ace.dijkstra.DijkstraAlgorithm;
import ro.ucv.ace.dijkstra.LinkedPriorityQueue;
import ro.ucv.ace.graph.Graph;
import ro.ucv.ace.graph.Vertex;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.locks.Lock;

/**
 * Created by Geo on 05.11.2016.
 */
public class ParallelDijkstraAlgorithm extends AbstractDijkstraAlgorithm implements DijkstraAlgorithm {

    private volatile Boolean done;

    private Lock lock;

    private final static int NO_THREADS = 4;

    public ParallelDijkstraAlgorithm(Graph graph, Boolean done, Lock lock) {
        super(graph);
        this.done = done;
        this.lock = lock;
    }


    protected void executeAlgorithm() {
        while (!weightMinQueue.isEmpty()) {
            Vertex u = weightMinQueue.poll();
            Set<Vertex> adjacentVertices = adjacentVerticesMap.get(u);
            done = false;

            for (Vertex v : adjacentVertices) {
                Double sum = distance.get(u) + distance(u, v);
                lock.lock();
                relaxEdges(u, v, sum);
                lock.unlock();
            }

            lock.lock();
            done = true;
            lock.unlock();
        }
    }

    private void relaxEdges(Vertex u, Vertex v, Double sum) {
        if (distance.get(v) > sum) {
            weightMinQueue.remove(v);
            v.setDistanceToSource(sum);
            weightMinQueue.add(v);

            distance.replace(v, sum);
            predecessors.replace(v, u);
        }
    }

    protected void initialize(Vertex source) {
        this.done = false;
        this.weightMinQueue = new LinkedPriorityQueue<>();
        this.distance = new HashMap<>();
        this.predecessors = new HashMap<>();
        adjacentVerticesMap = new HashMap<>();

        vertices.forEach(v -> adjacentVerticesMap.put(v, findAdjacentVertices(v)));

        vertices.forEach(v -> {
            distance.put(v, Double.POSITIVE_INFINITY);
            predecessors.put(v, null);
            v.setDistanceToSource(Double.POSITIVE_INFINITY);
            weightMinQueue.add(v);
        });

        distance.replace(source, 0.0);
        weightMinQueue.remove(source);
        source.setDistanceToSource(0.0);
        weightMinQueue.add(source);

        createHelpers();
    }

    private void createHelpers() {
        Runnable[] helpers = new Runnable[NO_THREADS];

        for (int i = 0; i < NO_THREADS; i++) {
            helpers[i] = new EdgeRelaxationHelper(i, weightMinQueue, adjacentVerticesMap, distance, predecessors, edges, lock, done);
        }

        for (Runnable helper : helpers) {
            (new Thread(helper)).start();
        }
    }

}
