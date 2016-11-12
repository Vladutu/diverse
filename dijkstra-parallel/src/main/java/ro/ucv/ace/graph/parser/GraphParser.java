package ro.ucv.ace.graph.parser;

import ro.ucv.ace.graph.model.Edge;
import ro.ucv.ace.graph.model.Graph;
import ro.ucv.ace.graph.model.Vertex;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Geo on 12.11.2016.
 */
public class GraphParser {

    public Graph readGraph(File file) {
        List<Vertex> vertices = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {

            int noVertices = scanner.nextInt();
            int noEdges = scanner.nextInt();

            for (int i = 1; i <= noVertices; i++) {
                vertices.add(new Vertex(i));
            }

            for (int i = 0; i < noEdges; i++) {
                int v1 = scanner.nextInt();
                int v2 = scanner.nextInt();
                double w = scanner.nextDouble();

                edges.add(new Edge(vertices.get(v1 - 1), vertices.get(v2 - 1), w));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Graph(vertices, edges);
    }
}
