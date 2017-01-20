package ro.ucv.ace.dijkstra.mpi;

import mpi.MPI;
import ro.ucv.ace.App;
import ro.ucv.ace.graph.model.Graph;
import ro.ucv.ace.graph.model.Vertex;
import ro.ucv.ace.graph.parser.GraphParser;
import ro.ucv.ace.minheap.NormalMinHeap;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Geo on 14.12.2016.
 */
public class Mpi {

    public static void main(String[] args) {
        MPI.Init(args);
        int me = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        if (me == 0) {
            // Create graph
            System.out.println("Starting reading graph");
            File file = new File(App.class.getClassLoader().getResource("data3.txt").getFile());
            GraphParser graphParser = new GraphParser();
            Graph graph = graphParser.readGraph(file);
            System.out.println("Graph loaded");

            long start = System.currentTimeMillis();
            executeMaster(graph, size);
            long end = System.currentTimeMillis();
            long total = end - start;

            System.out.println("----------------------------");
            System.out.println("Run time : " + total + " ms");
            System.out.println("----------------------------");
        }

        if (me != 0) {
            executeSlave(size, me);
        }

        MPI.Finalize();
    }

    private static void executeMaster(Graph graph, int size) {
        Vertex source = graph.getVertices().get(0);

        // Create necessary fields for algorithm
        NormalMinHeap weightMinQueue = new NormalMinHeap();
        Map<Vertex, Vertex> predecessors = new HashMap<>();

        // Populate fields for algorithm
        graph.getVertices().forEach(v -> {
            predecessors.put(v, null);
            v.setDistanceToSource(Double.POSITIVE_INFINITY);
            weightMinQueue.add(v);
        });
        weightMinQueue.updateDistance(source, 0.0);

        //send graph to slaves
        System.out.println("Seding graph to slaves");
        for (int i = 1; i < size; i++) {
            MPI.COMM_WORLD.Send(new Object[]{graph}, 0, 1, MPI.OBJECT, i, 0);
        }
        System.out.println("Graph sent");

        while (!weightMinQueue.isEmpty()) {
            Vertex u = weightMinQueue.poll();
            List<Vertex> adjacentVertices = graph.getAdjacentVertices(u).stream().collect(Collectors.toList());

            for (int i = 1; i < size; i++) {
                MPI.COMM_WORLD.Send(new Object[]{u, adjacentVertices}, 0, 2, MPI.OBJECT, i, 0);
            }

            for (int i = 1; i < size; i++) {
                Object[] message = new Object[1];
                MPI.COMM_WORLD.Recv(message, 0, 1, MPI.OBJECT, i, MPI.ANY_TAG);
                List<RelaxationResult> relaxationResults = (List<RelaxationResult>) message[0];


                for (RelaxationResult relaxationResult : relaxationResults) {
                    if (relaxationResult.isUpdate()) {
                        weightMinQueue.updateDistance(relaxationResult.getVertex(), relaxationResult.getValue());
                        predecessors.replace(relaxationResult.getVertex(), u);
                    }
                }
            }
        }

        System.out.println("DONE");
        System.out.println(findShortestPath(predecessors, graph.getVertices().get(1)));
    }

    private static void executeSlave(int size, int me) {
        Object[] g = new Object[1];
        MPI.COMM_WORLD.Recv(g, 0, 1, MPI.OBJECT, 0, MPI.ANY_TAG);
        Graph graph = (Graph) g[0];

        while (true) {
            Object[] message = new Object[2];
            MPI.COMM_WORLD.Recv(message, 0, 2, MPI.OBJECT, 0, MPI.ANY_TAG);
            Vertex u = (Vertex) message[0];
            List<Vertex> adjacentVertices = (List<Vertex>) message[1];
            List<RelaxationResult> relaxationResultList = new ArrayList<>();
            int step = size - 1;
            for (int i = me - 1; i < adjacentVertices.size(); i += step) {
                Vertex v = adjacentVertices.get(i);
                Double sum = u.getDistanceToSource() + graph.distanceBetween(u, v);
                if (v.getDistanceToSource() > sum) {
                    relaxationResultList.add(new RelaxationResult(true, sum, v));
                } else {
                    relaxationResultList.add(new RelaxationResult(false, 0.0, null));
                }
            }

            MPI.COMM_WORLD.Send(new Object[]{relaxationResultList}, 0, 1, MPI.OBJECT, 0, 0);
        }
    }

    private static List<Vertex> findShortestPath(Map<Vertex, Vertex> predecessors, Vertex destination) {
        List<Vertex> path = new ArrayList<>();
        Vertex step = destination;

        if (predecessors.get(step) == null) {
            return new ArrayList<>();
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }

        Collections.reverse(path);
        return path;
    }
}
