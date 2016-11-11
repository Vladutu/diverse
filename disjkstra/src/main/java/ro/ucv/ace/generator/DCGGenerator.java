package ro.ucv.ace.generator;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Geo on 05.11.2016.
 */
public class DCGGenerator {

    private List<String> lines = new ArrayList<>();

    private int noVertices;

    private int noEdges;

    private List<DCGEdge> edges = new ArrayList<>();

    private List<Integer> allVertices = new ArrayList<>();

    private Random random = new Random();

    public DCGGenerator(int noVertices, int noEdges) {
        this.noVertices = noVertices;
        this.noEdges = noEdges;

        for (int i = 0; i < noVertices; i++) {
            allVertices.add(i);
        }
    }

    public void generate(Path path) {
        generateDAG();
        addLines();
        writeToFile(path);
    }

    private void writeToFile(Path path) {
        try {
            Files.write(path, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addLines() {
        lines.add(noVertices + " " + noEdges);
        edges.forEach(e -> lines.add(e.getSource() + " " + e.getDestination() + " " + e.getCost()));
    }

    private void generateDAG() {
        int total = 0;

        for (int i = 0; i < noVertices - 1; i++) {
            edges.add(new DCGEdge(i, i + 1, Math.abs(random.nextInt() % 500)));
            edges.add(new DCGEdge(i + 1, i, Math.abs(random.nextInt() % 500)));
            total += 2;
        }

        while (total < noEdges) {
            DCGEdge DCGEdge = generateEdge();
            if (!edges.contains(DCGEdge)) {
                edges.add(DCGEdge);
                total++;
            }
        }

    }

    private DCGEdge generateEdge() {
        Collections.shuffle(allVertices);

        return new DCGEdge(allVertices.get(0), allVertices.get(1), Math.abs(random.nextInt() % 500));
    }
}
