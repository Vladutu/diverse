package ro.ucv.ace;

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
public class DAGGenerator {

    private List<String> lines = new ArrayList<>();

    private int noVertices;

    private int noEdges;

    private List<DAGEdge> edges = new ArrayList<>();

    List<Integer> allVertices = new ArrayList<>();

    Random random = new Random();

    public DAGGenerator(int noVertices, int noEdges) {
        this.noVertices = noVertices;
        this.noEdges = noEdges;

        for (int i = 0; i < noVertices; i++) {
            allVertices.add(i);
        }
    }

    public void generate(Path path) {
        generateDAG();

        lines.add(noVertices + " " + noEdges);
        edges.forEach(e -> lines.add(e.getSource() + " " + e.getDestination() + " " + e.getCost()));

        try {
            Files.write(path, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateDAG() {
        int total = 0;

        while (total < noEdges) {
            DAGEdge dagEdge = generateEdge();
            if (!edges.contains(dagEdge)) {
                edges.add(dagEdge);
                total++;
            }
        }
    }

    private DAGEdge generateEdge() {
        Collections.shuffle(allVertices);

        return new DAGEdge(allVertices.get(0), allVertices.get(1), Math.abs(random.nextInt() % 500));
    }
}
