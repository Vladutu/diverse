package ro.ucv.ace;

public interface EventAggregator {

    <S extends Subscriber> void subscribe(S subscriber);

    <E> void publish (E e);
}
