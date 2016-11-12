package ro.ucv.ace.dijkstra.sequential;


import ro.ucv.ace.dijkstra.DijkstraAlgorithm;
import ro.ucv.ace.minheap.NormalMinHeap;
import ro.ucv.ace.minheap.VertexMinHeap;
import ro.ucv.ace.graph.model.Graph;
import ro.ucv.ace.graph.model.Vertex;

import java.util.*;

/**
 * Created by Geo on 05.11.2016.
 */
public class SequentialDijkstraAlgorithm implements DijkstraAlgorithm {

    private VertexMinHeap weightMinQueue;

    private Map<Vertex, Vertex> predecessors;

    private Graph graph;

    public SequentialDijkstraAlgorithm(Graph graph) {
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

            adjacentVertices.forEach(v -> {
                Double sum = u.getDistanceToSource() + graph.distanceBetween(u, v);
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

        graph.getVertices().forEach(v -> {
            predecessors.put(v, null);
            v.setDistanceToSource(Double.POSITIVE_INFINITY);
            weightMinQueue.add(v);
        });

        weightMinQueue.updateDistance(source, 0.0);
    }
}
