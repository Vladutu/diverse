package ro.ucv.ace.dijkstra.sequential;

import ro.ucv.ace.dijkstra.DijkstraAlgorithm;
import ro.ucv.ace.graph.Edge;
import ro.ucv.ace.graph.Graph;
import ro.ucv.ace.graph.Vertex;
import ro.ucv.ace.minheap.NormalMinHeap;
import ro.ucv.ace.minheap.VertexMinHeap;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Geo on 05.11.2016.
 */
public class SequentialDijkstraAlgorithm implements DijkstraAlgorithm {

    private VertexMinHeap weightMinQueue;

    private Map<Vertex, Vertex> predecessors;

    private List<Vertex> vertices;

    private List<Edge> edges;

    private Map<Vertex, Set<Vertex>> adjacentVerticesMap;

    public SequentialDijkstraAlgorithm(Graph graph) {
        this.vertices = graph.getVertices();
        this.edges = graph.getEdges();
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
        LinkedList<Vertex> path = new LinkedList<>();
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

    private void executeAlgorithm() {
        while (!weightMinQueue.isEmpty()) {
            Vertex u = weightMinQueue.poll();
            Set<Vertex> adjacentVertices = adjacentVerticesMap.get(u);

            adjacentVertices.forEach(v -> {
                Double sum = u.getDistanceToSource() + distance(u, v);
                if (v.getDistanceToSource() > sum) {
                    weightMinQueue.updateDistance(v, sum);
                    predecessors.replace(v, u);
                }
            });
        }
    }

    private void initialize(Vertex source) {
        this.weightMinQueue = new NormalMinHeap();
        this.predecessors = new HashMap<>();
        adjacentVerticesMap = new HashMap<>();

        vertices.forEach(v -> adjacentVerticesMap.put(v, findAdjacentVertices(v)));

        vertices.forEach(v -> {
            predecessors.put(v, null);
            v.setDistanceToSource(Double.POSITIVE_INFINITY);
            weightMinQueue.add(v);
        });

        weightMinQueue.updateDistance(source, 0.0);
    }
}
