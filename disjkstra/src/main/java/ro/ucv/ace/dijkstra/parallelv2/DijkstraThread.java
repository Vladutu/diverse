package ro.ucv.ace.dijkstra.parallelv2;

import ro.ucv.ace.graph.Edge;
import ro.ucv.ace.graph.Vertex;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.locks.Lock;

/**
 * Created by Geo on 09.11.2016.
 */
public class DijkstraThread implements Runnable {


    private volatile Lock lock;

    private volatile SyncQueue<Edge> shared;

    private volatile Condition done;

    private volatile Map<Vertex, Vertex> predecessors;

    private List<Edge> edges;

    private volatile PriorityQueue<Vertex> weightMinQueue;

    public DijkstraThread(Lock lock, SyncQueue<Edge> shared, Condition done,
                          Map<Vertex, Vertex> predecessors, List<Edge> edges, PriorityQueue<Vertex> weightMinQueue) {
        this.lock = lock;
        this.shared = shared;
        this.done = done;
        this.predecessors = predecessors;
        this.edges = edges;
        this.weightMinQueue = weightMinQueue;
    }

    @Override
    public void run() {
        while (!done.isFlag()) {
            Edge e = shared.poll();
            if (e == null) {
                break;
            }
            Vertex u = e.getSource();
            Vertex v = e.getDestination();

            Double sum = u.getDistanceToSource() + distance(u, v);
            if (v.getDistanceToSource() > sum) {

                lock.lock();
                weightMinQueue.remove(v);
                v.setDistanceToSource(sum);
                weightMinQueue.add(v);
                lock.unlock();

                predecessors.replace(v, u);
            }
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
