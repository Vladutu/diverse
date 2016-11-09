package ro.ucv.ace.minheap;

import ro.ucv.ace.graph.Vertex;

import java.util.PriorityQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Geo on 10.11.2016.
 */
public class SynchronizedMinHeap implements VertexMinHeap {

    private PriorityQueue<Vertex> priorityQueue;

    private Lock lock;

    public SynchronizedMinHeap() {
        priorityQueue = new PriorityQueue<>((v1, v2) -> v1.getDistanceToSource().compareTo(v2.getDistanceToSource()));
        lock = new ReentrantLock();
    }

    @Override
    public void add(Vertex vertex) {
        lock.lock();
        try {
            priorityQueue.add(vertex);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Vertex poll() {
        lock.lock();
        try {
            return priorityQueue.poll();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        lock.lock();
        try {
            return priorityQueue.isEmpty();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void updateDistance(Vertex vertex, Double value) {
        lock.lock();
        try {
            priorityQueue.remove(vertex);
            vertex.setDistanceToSource(value);
            priorityQueue.add(vertex);
        } finally {
            lock.unlock();
        }
    }
}
