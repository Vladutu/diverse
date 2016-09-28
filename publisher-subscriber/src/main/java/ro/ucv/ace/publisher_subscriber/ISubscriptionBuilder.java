package ro.ucv.ace.publisher_subscriber;

import java.util.List;

/**
 * Created by Geo on 28.09.2016.
 */
public interface ISubscriptionBuilder {

    ISubscription build(ITask task, List<ITask> activeTasks);
}
