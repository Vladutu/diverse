package ro.ucv.ace.dijkstra.parallelv2;

import ro.ucv.ace.dijkstra.DijkstraAlgorithm;
import ro.ucv.ace.graph.Edge;
import ro.ucv.ace.graph.Graph;
import ro.ucv.ace.graph.Vertex;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * Created by Geo on 09.11.2016.
 */
public class BasicDijkstraAlgorithm implements DijkstraAlgorithm {

    private volatile Map<Vertex, Vertex> predecessors;

    private List<Vertex> vertices;

    private List<Edge> edges;

    private Map<Vertex, Set<Vertex>> adjacentVerticesMap;

    private volatile PriorityQueue<Vertex> weightMinQueue;

    private volatile Condition done;

    private volatile Lock lock;

    private volatile SyncQueue<Edge> shared;

    private static final int NO_THREADS = 4;

    public BasicDijkstraAlgorithm(Graph graph) {
        this.vertices = graph.getVertices();
        this.edges = graph.getEdges();
    }

    @Override
    public void execute(Vertex source) {
        initialize(source);

        long start = System.currentTimeMillis();
        executeAlgorithm();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    @Override
    public List<Vertex> findShortestPath(Vertex destination) {
        LinkedList<Vertex> path = new LinkedList<Vertex>();
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
                lock.lock();
                Vertex u = weightMinQueue.poll();
                lock.unlock();
                Set<Vertex> adjacentVertices = adjacentVerticesMap.get(u);

                adjacentVertices.forEach(v -> {
                    shared.add(new Edge(u, v, 0.0));
                });


                while (!shared.isEmpty()) {
                    try {
                        shared.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

            done.setFlag(true);
            shared.notifyAll();
        }
    }

    private void initialize(Vertex source) {
        this.weightMinQueue = new PriorityQueue<>((v1, v2) -> v1.getDistanceToSource().compareTo(v2.getDistanceToSource()));
        this.predecessors = new HashMap<>();
        this.done = new Condition(false);
        this.lock = new ReentrantLock();
        this.shared = new SyncQueue<>(done);
        this.adjacentVerticesMap = new HashMap<>();

        vertices.forEach(v -> adjacentVerticesMap.put(v, findAdjacentVertices(v)));

        vertices.forEach(v -> {
            predecessors.put(v, null);
            v.setDistanceToSource(Double.POSITIVE_INFINITY);
            weightMinQueue.add(v);
        });

        weightMinQueue.remove(source);
        source.setDistanceToSource(0.0);
        weightMinQueue.add(source);

        createThreads();
    }

    private void createThreads() {
        for (int i = 0; i < NO_THREADS; i++) {
            DijkstraThread dijkstraThread = new DijkstraThread(lock, shared, done, predecessors, edges, weightMinQueue);
            (new Thread(dijkstraThread)).start();
        }
    }

    private Set<Vertex> findAdjacentVertices(Vertex vertex) {
        return edges.stream()
                .filter(e -> e.getSource().equals(vertex))
                .map(Edge::getDestination)
                .collect(Collectors.toSet());
    }

}
