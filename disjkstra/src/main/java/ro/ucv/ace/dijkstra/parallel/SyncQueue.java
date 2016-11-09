package ro.ucv.ace.dijkstra.parallel;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Geo on 09.11.2016.
 */
public class SyncQueue<T> {

    private Deque<T> deque = new ArrayDeque<>();

    private volatile AtomicBoolean done;

    private final int noThreads;

    private int currentWaiting = 0;

    public SyncQueue(AtomicBoolean done, int noThreads) {
        this.done = done;
        this.noThreads = noThreads;
    }

    public synchronized void add(T item) {
        deque.push(item);

        if (deque.size() == 1) {
            notifyAll();
        }
    }

    public synchronized T poll() {
        while (deque.isEmpty()) {
            if (done.get()) {
                return null;
            }

            try {
                currentWaiting++;
                if (currentWaiting == noThreads) {
                    notifyAll();
                }
                wait();
                currentWaiting--;
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
