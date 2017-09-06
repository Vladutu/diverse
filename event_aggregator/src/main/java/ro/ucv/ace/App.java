package ro.ucv.ace;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
        EventAggregator eventAggregator = new SimpleEventAggregator();
        eventAggregator.subscribe(new TestSubscriber());
        eventAggregator.subscribe(new TestSubscriber());
        eventAggregator.subscribe(new TestSubscriber());
        eventAggregator.subscribe(new TestSubscriber());
        eventAggregator.publish(new TestEvent());

    }
}
