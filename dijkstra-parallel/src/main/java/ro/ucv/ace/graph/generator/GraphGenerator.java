package ro.ucv.ace.graph.generator;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by Geo on 12.11.2016.
 */
public class GraphGenerator {

    private int noEdges;

    private int noVertices;

    private Random random = new Random();

    private List<Integer> vertices;

    private int totalEdgesGenerated = 0;

    private Table<Integer, Integer, Integer> edges;

    public GraphGenerator(int noVertices, int noEdges) {
        this.noEdges = noEdges;
        this.noVertices = noVertices;
    }

    public void generate(File file) {
        initialize();
        generateMinimumConnectedGraph();
        generateRemainingEdges();

        writeToFile(file);
    }

    private void writeToFile(File file) {
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.print(noVertices + " " + noEdges + "\n");

            Map<Integer, Map<Integer, Integer>> map = edges.columnMap();

            for (Map.Entry<Integer, Map<Integer, Integer>> outerEntry : map.entrySet()) {
                int v1 = outerEntry.getKey();
                for (Map.Entry<Integer, Integer> innerEntry : outerEntry.getValue().entrySet()) {
                    int v2 = innerEntry.getKey();
                    int w = innerEntry.getValue();

                    writer.print(v1 + " " + v2 + " " + w + "\n");
                }
            }

            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void generateRemainingEdges() {
        while (totalEdgesGenerated < noEdges) {
            int v1 = generateRandomVertex();
            int v2 = generateRandomVertex();

            if (!(edges.contains(v1, v2) || edges.contains(v2, v1) || v1 == v2)) {
                int w = generateRandomWeight();
                edges.put(v1, v2, w);

                totalEdgesGenerated++;
            }
        }
    }

    private void initialize() {
        vertices = new ArrayList<>();
        edges = HashBasedTable.create();

        for (int i = 1; i <= noVertices; i++) {
            vertices.add(i);
        }
        Collections.shuffle(vertices);
        totalEdgesGenerated = 0;
    }

    private void generateMinimumConnectedGraph() {
        List<Integer> settledVertices = new ArrayList<>();
        int index = 0;
        Integer firstVertex = vertices.get(index);
        settledVertices.add(firstVertex);
        index++;

        while (index < vertices.size()) {
            Integer vertex = vertices.get(index);
            int randomIndex = Math.abs(random.nextInt()) % settledVertices.size();

            Integer randomSettledVertex = settledVertices.get(randomIndex);
            settledVertices.add(vertex);
            edges.put(vertex, randomSettledVertex, generateRandomWeight());

            totalEdgesGenerated++;
            index++;
        }
    }

    private int generateRandomWeight() {
        return Math.abs(random.nextInt() % 500);
    }

    private int generateRandomVertex() {
        return Math.abs(random.nextInt()) % noVertices + 1;
    }


}
