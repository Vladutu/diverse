package ro.ucv.ace;

import ro.ucv.ace.publisher_subscriber.IEventEmitter;
import ro.ucv.ace.publisher_subscriber.ISubscription;
import ro.ucv.ace.publisher_subscriber.ISubscriptionBuilder;
import ro.ucv.ace.publisher_subscriber.impl.EventEmitter;
import ro.ucv.ace.publisher_subscriber.impl.SubscriptionBuilder;
import ro.ucv.ace.test.Student;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        ISubscriptionBuilder subscriptionBuilder = new SubscriptionBuilder();
        IEventEmitter<Student> eventEmitter = new EventEmitter<>(subscriptionBuilder);

        Student student = new Student("Geo", 22, eventEmitter);

        ISubscription subscribe = eventEmitter.subscribe(s -> System.out.println(s));

        student.update("Teo", 27);

        subscribe.cancel();

        student.update("Geo", 66);
    }
}
