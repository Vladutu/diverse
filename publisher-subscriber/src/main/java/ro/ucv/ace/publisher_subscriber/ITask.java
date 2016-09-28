package ro.ucv.ace.publisher_subscriber;

/**
 * Created by Geo on 28.09.2016.
 */
@FunctionalInterface
public interface ITask<T> {

    void execute(T t);
}
