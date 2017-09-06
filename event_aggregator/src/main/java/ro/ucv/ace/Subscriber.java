package ro.ucv.ace;

public interface Subscriber<T> {

    void onEvent (T t);
}
