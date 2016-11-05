package ro.ucv.ace;

import ro.ucv.ace.graph.Edge;
import ro.ucv.ace.graph.Graph;
import ro.ucv.ace.graph.Vertex;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Geo on 05.11.2016.
 */
public class GraphParser {

    public Graph readGraph() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("dag.txt").getFile());

        List<Vertex> vertices = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {

            int noVertices = scanner.nextInt();
            int noEdges = scanner.nextInt();

            for (int i = 0; i < noVertices; i++) {
                vertices.add(new Vertex(i));
            }

            for (int i = 0; i < noEdges; i++) {
                int source = scanner.nextInt();
                int dest = scanner.nextInt();
                double cost = scanner.nextDouble();

                edges.add(new Edge(vertices.get(source), vertices.get(dest), cost));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Graph(vertices, edges);
    }
}
