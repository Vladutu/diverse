package ro.ucv.ace.publisher_subscriber;

/**
 * Created by Geo on 28.09.2016.
 */
public interface IEventEmitter<T> {

    ISubscription subscribe(ITask task);

    void emit(T t);
}
