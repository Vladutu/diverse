package ro.ucv.ace;

import ro.ucv.ace.dijkstra.DijkstraAlgorithm;
import ro.ucv.ace.dijkstra.sequential.SequentialDijkstraAlgorithm;
import ro.ucv.ace.graph.model.Graph;
import ro.ucv.ace.graph.parser.GraphParser;

import java.io.File;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
//        GraphGenerator graphGenerator = new GraphGenerator(10000, 1000000);
//        graphGenerator.generate(new File("D:\\dag.txt"));

        System.out.println("Starting graph parsing...");
        File file = new File(App.class.getClassLoader().getResource("dag.txt").getFile());
        GraphParser graphParser = new GraphParser();
        Graph graph = graphParser.readGraph(file);

        System.out.println("Finished parsing graph.\nStarting dijkstra algorithm...");

        DijkstraAlgorithm dijkstraAlgorithm = new SequentialDijkstraAlgorithm(graph);
//        DijkstraAlgorithm dijkstraAlgorithm = new ParallelDijkstraAlgorithm(graph);

        dijkstraAlgorithm.execute(graph.getVertices().get(0));

        System.out.println(dijkstraAlgorithm.findShortestPath(graph.getVertices().get(1)));
    }
}
