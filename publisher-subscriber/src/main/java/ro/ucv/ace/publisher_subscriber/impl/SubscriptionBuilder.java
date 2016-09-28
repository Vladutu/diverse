package ro.ucv.ace.publisher_subscriber.impl;

import ro.ucv.ace.publisher_subscriber.ISubscription;
import ro.ucv.ace.publisher_subscriber.ISubscriptionBuilder;
import ro.ucv.ace.publisher_subscriber.ITask;

import java.util.List;

/**
 * Created by Geo on 28.09.2016.
 */
public class SubscriptionBuilder implements ISubscriptionBuilder {

    @Override
    public ISubscription build(ITask task, List<ITask> activeTasks) {
        return new Subscription(task, activeTasks);
    }
}
