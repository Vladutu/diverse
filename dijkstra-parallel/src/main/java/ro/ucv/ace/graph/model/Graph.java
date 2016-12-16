package ro.ucv.ace.graph.model;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Geo on 12.11.2016.
 */
public class Graph implements Serializable {

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
            distances.put(e.getVertex1(), e.getVertex2(), e.getWeight());
        });
    }

    public Set<Vertex> getAdjacentVertices(Vertex u) {
        return adjacentVerticesMap.get(u);
    }

    public Double distanceBetween(Vertex u, Vertex v) {
        if (distances.get(u, v) != null) {
            return distances.get(u, v);
        } else if (distances.get(v, u) != null) {
            return distances.get(v, u);
        }
        throw new RuntimeException();
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    private Set<Vertex> findAdjacentVertices(Vertex vertex) {
        return edges.parallelStream()
                .filter(e -> e.getVertex1().equals(vertex) || e.getVertex2().equals(vertex))
                .map(e -> {
                    if (e.getVertex1().equals(vertex)) {
                        return e.getVertex2();
                    }

                    return e.getVertex1();
                })
                .collect(Collectors.toSet());
    }
}
