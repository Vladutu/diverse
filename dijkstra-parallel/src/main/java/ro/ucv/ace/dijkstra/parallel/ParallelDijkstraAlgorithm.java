package ro.ucv.ace.dijkstra.parallel;


import ro.ucv.ace.dijkstra.DijkstraAlgorithm;
import ro.ucv.ace.minheap.SynchronizedMinHeap;
import ro.ucv.ace.minheap.VertexMinHeap;
import ro.ucv.ace.graph.model.Edge;
import ro.ucv.ace.graph.model.Graph;
import ro.ucv.ace.graph.model.Vertex;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Geo on 09.11.2016.
 */
public class ParallelDijkstraAlgorithm implements DijkstraAlgorithm {

    private Graph graph;

    private Map<Vertex, Vertex> predecessors;

    private VertexMinHeap weightMinQueue;

    private AtomicBoolean done;

    private SyncQueue<Edge> shared;

    private static final int NO_THREADS = 4;

    public ParallelDijkstraAlgorithm(Graph graph) {
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
        synchronized (shared) {
            while (!weightMinQueue.isEmpty()) {
                Vertex u = weightMinQueue.poll();
                Set<Vertex> adjacentVertices = graph.getAdjacentVertices(u);

                adjacentVertices.forEach(v -> shared.add(new Edge(u, v, 0.0)));

                while (!shared.isEmpty()) {
                    try {
                        shared.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

            done.set(true);
            shared.notifyAll();
        }
    }

    private void initialize(Vertex source) {
        this.weightMinQueue = new SynchronizedMinHeap();
        this.predecessors = new HashMap<>();
        this.done = new AtomicBoolean(false);
        this.shared = new SyncQueue<>(done, NO_THREADS);

        graph.getVertices().forEach(v -> {
            predecessors.put(v, null);
            v.setDistanceToSource(Double.POSITIVE_INFINITY);
            weightMinQueue.add(v);
        });

        weightMinQueue.updateDistance(source, 0.0);

        createThreads();
    }

    private void createThreads() {
        for (int i = 0; i < NO_THREADS; i++) {
            DijkstraThread dijkstraThread = new DijkstraThread(graph, shared, done, predecessors, weightMinQueue);
            (new Thread(dijkstraThread)).start();
        }
    }
}
