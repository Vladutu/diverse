package ro.ucv.ace.dijkstra.helperThread;

import ro.ucv.ace.dijkstra.DijkstraAlgorithm;
import ro.ucv.ace.graph.Graph;
import ro.ucv.ace.graph.Vertex;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Geo on 11.11.2016.
 */
public class HTParallelDijkstraAlgorithm implements DijkstraAlgorithm {

    private Graph graph;

    private AtomicBoolean done;

    private Map<Vertex, Vertex> predecessors;

    private VertexPriorityQueue weightMinQueue;

    private Lock lock;

    private final static int NO_THREADS = 3;

    public HTParallelDijkstraAlgorithm(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void execute(Vertex source) {
        initialize(source);

        long start = System.currentTimeMillis();
        executeAlgorithm();
        long end = System.currentTimeMillis();
        long total = end - start;

        System.out.println("----------------------------");
        System.out.println("Run time : " + total + " ms");
        System.out.println("----------------------------");
    }

    @Override
    public List<Vertex> findShortestPath(Vertex destination) {
        List<Vertex> path = new ArrayList<>();
        Vertex step = destination;

        if (predecessors.get(step) == null) {
            return new ArrayList<>();
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }

        Collections.reverse(path);
        return path;
    }

    private void executeAlgorithm() {
        while (!weightMinQueue.isEmpty()) {
            Vertex u = weightMinQueue.poll();
            Set<Vertex> adjacentVertices = graph.getAdjacentVertices(u);
            done.set(false);

            for (Vertex v : adjacentVertices) {
                Double sum = u.getDistanceToSource() + graph.distanceBetween(u, v);
                lock.lock();
                try {
                    relaxEdges(u, v, sum);

                } finally {
                    lock.unlock();
                }
            }

            lock.lock();
            try {
                done.set(true);
            } finally {
                lock.unlock();
            }
        }
    }

    private void relaxEdges(Vertex u, Vertex v, Double sum) {
        if (v.getDistanceToSource() > sum) {
            weightMinQueue.updateDistance(v, sum);
            predecessors.replace(v, u);
        }
    }

    private void initialize(Vertex source) {
        this.weightMinQueue = new SyncLinkedVertexPriorityQueue();
        this.predecessors = new HashMap<>();
        this.done = new AtomicBoolean(true);
        this.lock = new ReentrantLock();

        graph.getVertices().forEach(v -> {
            predecessors.put(v, null);
            v.setDistanceToSource(Double.POSITIVE_INFINITY);
            weightMinQueue.add(v);
        });

        weightMinQueue.updateDistance(source, 0.0);

        createHelpers();
    }

    private void createHelpers() {
        AtomicBoolean stop = new AtomicBoolean(false);

        for (int i = 0; i < NO_THREADS; i++) {
            (new Thread(new EdgeRelaxationHelper(i, graph, predecessors, weightMinQueue, lock, done, stop))).start();
        }
    }


}
