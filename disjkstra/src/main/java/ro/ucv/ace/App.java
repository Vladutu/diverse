package ro.ucv.ace;

import ro.ucv.ace.dijkstra.DijkstraAlgorithm;
import ro.ucv.ace.dijkstra.parallel.ParallelDijkstraAlgorithm;
import ro.ucv.ace.graph.Edge;
import ro.ucv.ace.graph.Graph;
import ro.ucv.ace.graph.Vertex;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        GraphParser graphParser = new GraphParser();
        Graph graph = graphParser.readGraph();
        Boolean done = false;
        Lock lock = new ReentrantLock();

        //DijkstraAlgorithm dijkstraAlgorithm = new SequentialDijkstraAlgorithm(graph);
        DijkstraAlgorithm dijkstraAlgorithm = new ParallelDijkstraAlgorithm(graph, done, lock);

        dijkstraAlgorithm.execute(graph.getVertices().get(0));

        dijkstraAlgorithm.findShortestPath(graph.getVertices().get(10)).forEach(System.out::println);

//        DAGGenerator generator = new DAGGenerator(500, 50000);
//        generator.generate(Paths.get("D:\\dag.txt"));
    }

    private static void addEdges(List<Edge> edges, List<Vertex> vertices) {
        edges.add(createEdge(0, 1, 85.0, vertices));
        edges.add(createEdge(1, 0, 40, vertices));
        edges.add(createEdge(0, 2, 217, vertices));
        edges.add(createEdge(0, 4, 173, vertices));
        edges.add(createEdge(2, 6, 186, vertices));
        edges.add(createEdge(2, 7, 103, vertices));
        edges.add(createEdge(3, 7, 183, vertices));
        edges.add(createEdge(5, 8, 250, vertices));
        edges.add(createEdge(8, 9, 84, vertices));
        edges.add(createEdge(7, 9, 167, vertices));
        edges.add(createEdge(4, 9, 502, vertices));
        edges.add(createEdge(9, 10, 40, vertices));
        edges.add(createEdge(1, 10, 600, vertices));
    }

    private static Edge createEdge(int source, int dest, double weight, List<Vertex> vertices) {
        return new Edge(vertices.get(source), vertices.get(dest), weight);
    }
}
