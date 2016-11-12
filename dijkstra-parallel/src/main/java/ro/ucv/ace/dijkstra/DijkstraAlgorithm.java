package ro.ucv.ace.dijkstra;

import ro.ucv.ace.graph.model.Vertex;

import java.util.List;

/**
 * Created by Geo on 05.11.2016.
 */
public interface DijkstraAlgorithm {

    void execute(Vertex source);

    List<Vertex> findShortestPath(Vertex destination);
}
