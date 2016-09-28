package ro.ucv.ace.publisher_subscriber.impl;

import ro.ucv.ace.publisher_subscriber.IEventEmitter;
import ro.ucv.ace.publisher_subscriber.ISubscription;
import ro.ucv.ace.publisher_subscriber.ISubscriptionBuilder;
import ro.ucv.ace.publisher_subscriber.ITask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Geo on 28.09.2016.
 */
public class EventEmitter<T> implements IEventEmitter<T> {

    private List<ITask> activeTasks;

    private ISubscriptionBuilder subscriptionBuilder;

    public EventEmitter(ISubscriptionBuilder subscriptionBuilder) {
        this.subscriptionBuilder = subscriptionBuilder;
        activeTasks = new ArrayList<>();
    }

    @Override
    public ISubscription subscribe(ITask task) {
        activeTasks.add(task);

        return subscriptionBuilder.build(task, activeTasks);
    }

    @Override
    public void emit(T t) {
        activeTasks.forEach(task -> task.execute(t));
    }
}
