package ro.ucv.ace.dijkstra.parallel;

import ro.ucv.ace.LinkedPriorityQueue;
import ro.ucv.ace.graph.Edge;
import ro.ucv.ace.graph.Vertex;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;

/**
 * Created by Geo on 05.11.2016.
 */
public class EdgeRelaxationHelper implements Runnable {

    private int id;

    private LinkedPriorityQueue<Vertex> weightMinQueue;

    private Map<Vertex, Set<Vertex>> adjacentVerticesMap;

    private Map<Vertex, Double> distance;

    private Map<Vertex, Vertex> predecessors;

    private List<Edge> edges;

    private Lock lock;

    private volatile Boolean done;

    private volatile boolean stop;


    public EdgeRelaxationHelper(int id, LinkedPriorityQueue<Vertex> weightMinQueue,
                                Map<Vertex, Set<Vertex>> adjacentVerticesMap, Map<Vertex, Double> distance,
                                Map<Vertex, Vertex> predecessors, List<Edge> edges, Lock lock, Boolean done) {
        this.id = id;
        this.weightMinQueue = weightMinQueue;
        this.adjacentVerticesMap = adjacentVerticesMap;
        this.distance = distance;
        this.predecessors = predecessors;
        this.edges = edges;
        this.lock = lock;
        this.done = done;
    }

    @Override
    public void run() {
        while (!weightMinQueue.isEmpty()) {
            while (done) {
                stop = false;
                Vertex vertex = weightMinQueue.get(id);

                if (vertex != null) {
                    Set<Vertex> adjacent = adjacentVerticesMap.get(vertex);

                    for (Vertex y : adjacent) {
                        if (!stop) {
                            lock.lock();
                            if (!done) {
                                relaxEdges(vertex, y);
                            } else {
                                stop = true;
                            }
                            lock.unlock();
                        }
                    }
                }
            }
        }
    }

    private void relaxEdges(Vertex x, Vertex y) {
        Double sum = distance.get(x) + distance(x, y);

        if (distance.get(y) > sum) {
            weightMinQueue.remove(y);
            y.setDistanceToSource(sum);
            weightMinQueue.add(y);

            distance.replace(y, sum);
            predecessors.replace(y, x);
        }
    }

    private Double distance(Vertex u, Vertex v) {
        return edges.stream()
                .filter(e -> e.getSource().equals(u) && e.getDestination().equals(v))
                .findFirst()
                .get()
                .getWeight();
    }
}
