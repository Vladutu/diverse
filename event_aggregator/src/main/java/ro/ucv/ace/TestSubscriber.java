package ro.ucv.ace;

public class TestSubscriber implements Subscriber<TestEvent> {

    public void onEvent(TestEvent testEvent) {
        System.out.println("Test event caught");
    }
}
