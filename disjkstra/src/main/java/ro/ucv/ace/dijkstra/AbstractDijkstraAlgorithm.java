package ro.ucv.ace.dijkstra;

import ro.ucv.ace.graph.Edge;
import ro.ucv.ace.graph.Graph;
import ro.ucv.ace.graph.Vertex;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Geo on 06.11.2016.
 */
public abstract class AbstractDijkstraAlgorithm implements DijkstraAlgorithm {

    protected LinkedPriorityQueue<Vertex> weightMinQueue;

    protected Map<Vertex, Double> distance;

    protected Map<Vertex, Vertex> predecessors;

    protected List<Vertex> vertices;

    protected List<Edge> edges;

    protected Map<Vertex, Set<Vertex>> adjacentVerticesMap;


    public AbstractDijkstraAlgorithm(Graph graph) {
        this.weightMinQueue = new LinkedPriorityQueue<>();
        this.distance = new HashMap<>();
        this.predecessors = new HashMap<>();
        this.vertices = graph.getVertices();
        this.edges = graph.getEdges();
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

    @Override
    public void execute(Vertex source) {
        initialize(source);

        long start = System.currentTimeMillis();
        executeAlgorithm();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    protected abstract void executeAlgorithm();

    protected abstract void initialize(Vertex source);

    protected Double distance(Vertex u, Vertex v) {
        return edges.stream()
                .filter(e -> e.getSource().equals(u) && e.getDestination().equals(v))
                .findFirst()
                .get()
                .getWeight();
    }

    protected Set<Vertex> findAdjacentVertices(Vertex vertex) {
        return edges.stream()
                .filter(e -> e.getSource().equals(vertex))
                .map(Edge::getDestination)
                .collect(Collectors.toSet());
    }

}
