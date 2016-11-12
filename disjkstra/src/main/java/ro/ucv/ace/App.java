package ro.ucv.ace;

import ro.ucv.ace.dijkstra.DijkstraAlgorithm;
import ro.ucv.ace.dijkstra.sequential.SequentialDijkstraAlgorithm;
import ro.ucv.ace.graph.Graph;
import ro.ucv.ace.parser.GraphParser;

import java.io.File;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

        File file = new File(App.class.getClassLoader().getResource("dag.txt").getFile());

        GraphParser graphParser = new GraphParser();
        Graph graph = graphParser.readGraph(file);

        DijkstraAlgorithm dijkstraAlgorithm = new SequentialDijkstraAlgorithm(graph);
        //DijkstraAlgorithm dijkstraAlgorithm = new ParallelDijkstraAlgorithm(graph);
        // DijkstraAlgorithm dijkstraAlgorithm = new HTParallelDijkstraAlgorithm(graph);
        dijkstraAlgorithm.execute(graph.getVertices().get(0));

        dijkstraAlgorithm.findShortestPath(graph.getVertices().get(10)).forEach(System.out::println);

//        DCGGenerator generator = new DCGGenerator(10000, 100000);
//        generator.generate(Paths.get("D:\\dag.txt"));
    }

}
