package ro.ucv.ace.dijkstra.sequential;

import ro.ucv.ace.dijkstra.AbstractDijkstraAlgorithm;
import ro.ucv.ace.dijkstra.DijkstraAlgorithm;
import ro.ucv.ace.dijkstra.LinkedPriorityQueue;
import ro.ucv.ace.graph.Graph;
import ro.ucv.ace.graph.Vertex;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by Geo on 05.11.2016.
 */
public class SequentialDijkstraAlgorithm extends AbstractDijkstraAlgorithm implements DijkstraAlgorithm {

    public SequentialDijkstraAlgorithm(Graph graph) {
        super(graph);
    }

    protected void executeAlgorithm() {
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

    protected void initialize(Vertex source) {
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
