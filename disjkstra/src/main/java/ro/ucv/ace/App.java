package ro.ucv.ace;

import ro.ucv.ace.dijkstra.DijkstraAlgorithm;
import ro.ucv.ace.dijkstra.parallelv2.BasicDijkstraAlgorithm;
import ro.ucv.ace.graph.Graph;
import ro.ucv.ace.parser.GraphParser;

import java.io.File;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

        File file = new File(App.class.getClassLoader().getResource("dag.txt").getFile());

        GraphParser graphParser = new GraphParser();
        Graph graph = graphParser.readGraph(file);
        Boolean done = true;
        Lock lock = new ReentrantLock();

         //DijkstraAlgorithm dijkstraAlgorithm = new SequentialDijkstraAlgorithm(graph);
        //DijkstraAlgorithm dijkstraAlgorithm = new HTParallelDijkstraAlgorithm(graph, done, lock);
        DijkstraAlgorithm dijkstraAlgorithm = new BasicDijkstraAlgorithm(graph);

        dijkstraAlgorithm.execute(graph.getVertices().get(0));

        dijkstraAlgorithm.findShortestPath(graph.getVertices().get(10)).forEach(System.out::println);

//        DCGGenerator generator = new DCGGenerator(650, 100000);
//        generator.generate(Paths.get("D:\\dag.txt"));
    }

}
