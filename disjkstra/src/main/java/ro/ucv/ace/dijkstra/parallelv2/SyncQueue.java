package ro.ucv.ace.dijkstra.parallelv2;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by Geo on 09.11.2016.
 */
public class SyncQueue<T> {

    private Deque<T> deque = new ArrayDeque<>();

    private Condition done;

    public SyncQueue(Condition done) {
        this.done = done;
    }

    public synchronized void add(T item) {
        deque.push(item);

        if (deque.size() == 1) {
            notifyAll();
        }
    }

    public synchronized T poll() {
        if (deque.isEmpty()) {
            notifyAll();
        }

        while (deque.isEmpty()) {
            if (done.isFlag()) {
                return null;
            }
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return deque.poll();
    }

    public synchronized boolean isEmpty() {
        return deque.isEmpty();
    }

}
