package ro.ucv.ace.graph;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Geo on 05.11.2016.
 */
public class Graph {

    private List<Vertex> vertices;

    private List<Edge> edges;

    private Map<Vertex, Set<Vertex>> adjacentVerticesMap;

    private Table<Vertex, Vertex, Double> distances;

    public Graph(List<Vertex> vertices, List<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
        adjacentVerticesMap = new HashMap<>();
        distances = HashBasedTable.create();

        vertices.forEach(v -> adjacentVerticesMap.put(v, findAdjacentVertices(v)));
        edges.forEach(e -> {
            distances.put(e.getSource(), e.getDestination(), e.getWeight());
        });
    }

    private Set<Vertex> findAdjacentVertices(Vertex vertex) {
        return edges.parallelStream()
                .filter(e -> e.getSource().equals(vertex))
                .map(Edge::getDestination)
                .collect(Collectors.toSet());
    }

    public Set<Vertex> getAdjacentVertices(Vertex u) {
        return adjacentVerticesMap.get(u);
    }

    public Double distanceBetween(Vertex u, Vertex v) {
//        return edges.stream()
//                .filter(e -> e.getSource().equals(u) && e.getDestination().equals(v))
//                .findFirst()
//                .get()
//                .getWeight();
        return distances.get(u, v);
    }

    public List<Vertex> getVertices() {
        return vertices;
    }
}
