package ro.ucv.ace.dijkstra.parallel;

import ro.ucv.ace.LinkedPriorityQueue;
import ro.ucv.ace.dijkstra.DijkstraAlgorithm;
import ro.ucv.ace.graph.Edge;
import ro.ucv.ace.graph.Graph;
import ro.ucv.ace.graph.Vertex;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

/**
 * Created by Geo on 05.11.2016.
 */
public class ParallelDijkstraAlgorithm implements DijkstraAlgorithm {

    private Graph graph;

    private LinkedPriorityQueue<Vertex> weightMinQueue;

    private Map<Vertex, Double> distance;

    private Map<Vertex, Vertex> predecessors;

    private List<Vertex> vertices;

    private List<Edge> edges;

    private Map<Vertex, Set<Vertex>> adjacentVerticesMap;

    private volatile Boolean done;

    private Lock lock;

    private final static int NO_THREADS = 4;

    public ParallelDijkstraAlgorithm(Graph graph, Boolean done, Lock lock) {
        this.graph = graph;
        this.weightMinQueue = new LinkedPriorityQueue<>();
        this.distance = new HashMap<>();
        this.predecessors = new HashMap<>();
        this.vertices = graph.getVertices();
        this.edges = graph.getEdges();
        this.done = done;
        this.lock = lock;
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
        LinkedList<Vertex> path = new LinkedList<>();
        Vertex step = destination;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return new ArrayList<>();
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }

    private void executeAlgorithm() {
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

    private Double distance(Vertex u, Vertex v) {
        return edges.stream()
                .filter(e -> e.getSource().equals(u) && e.getDestination().equals(v))
                .findFirst()
                .get()
                .getWeight();
    }

    private void initialize(Vertex source) {
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

    private Set<Vertex> findAdjacentVertices(Vertex vertex) {
        return edges.stream()
                .filter(e -> e.getSource().equals(vertex))
                .map(Edge::getDestination)
                .collect(Collectors.toSet());
    }
}
