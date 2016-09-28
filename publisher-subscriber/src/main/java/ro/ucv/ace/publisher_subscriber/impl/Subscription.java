package ro.ucv.ace.publisher_subscriber.impl;

import ro.ucv.ace.publisher_subscriber.ISubscription;
import ro.ucv.ace.publisher_subscriber.ITask;

import java.util.List;

/**
 * Created by Geo on 28.09.2016.
 */
public class Subscription implements ISubscription {

    private final ITask task;

    private final List<ITask> activeTasks;

    public Subscription(ITask task, List<ITask> activeTasks) {
        this.task = task;
        this.activeTasks = activeTasks;
    }

    @Override
    public void cancel() {
        activeTasks.remove(task);
    }
}
