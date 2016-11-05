package ro.ucv.ace.dijkstra.sequential;

import ro.ucv.ace.LinkedPriorityQueue;
import ro.ucv.ace.dijkstra.DijkstraAlgorithm;
import ro.ucv.ace.graph.Edge;
import ro.ucv.ace.graph.Graph;
import ro.ucv.ace.graph.Vertex;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Geo on 05.11.2016.
 */
public class SequentialDijkstraAlgorithm implements DijkstraAlgorithm {

    private Graph graph;

    private LinkedPriorityQueue<Vertex> weightMinQueue;

    private Map<Vertex, Double> distance;

    private Map<Vertex, Vertex> predecessors;

    private List<Vertex> vertices;

    private List<Edge> edges;

    private Map<Vertex, Set<Vertex>> adjacentVerticesMap;

    public SequentialDijkstraAlgorithm(Graph graph) {
        this.graph = graph;
        //this.weightMinQueue = new PriorityQueue<>((v1, v2) -> v1.getDistanceToSource().compareTo(v2.getDistanceToSource()));
        this.weightMinQueue = new LinkedPriorityQueue<>();
        this.distance = new HashMap<>();
        this.predecessors = new HashMap<>();
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

            adjacentVertices.forEach(v -> {
                Double sum = distance.get(u) + distance(u, v);
                if (distance.get(v) > sum) {
                    weightMinQueue.remove(v);
                    v.setDistanceToSource(sum);
                    weightMinQueue.add(v);

                    distance.replace(v, sum);
                    predecessors.replace(v, u);
                }
            });
        }
    }

    private Double distance(Vertex u, Vertex v) {
        return edges.stream()
                .filter(e -> e.getSource().equals(u) && e.getDestination().equals(v))
                .findFirst()
                .get()
                .getWeight();
    }

    private Set<Vertex> findAdjacentVertices(Vertex vertex) {
        return edges.stream()
                .filter(e -> e.getSource().equals(vertex))
                .map(Edge::getDestination)
                .collect(Collectors.toSet());
    }


    private void initialize(Vertex source) {
        // this.weightMinQueue = new PriorityQueue<>((v1, v2) -> v1.getDistanceToSource().compareTo(v2.getDistanceToSource()));
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
    }
}
