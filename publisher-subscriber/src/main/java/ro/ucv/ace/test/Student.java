package ro.ucv.ace.test;

import ro.ucv.ace.publisher_subscriber.IEventEmitter;

/**
 * Created by Geo on 28.09.2016.
 */
public class Student {

    private String name;

    private int age;

    private IEventEmitter<Student> eventEmitter;

    public Student(String name, int age, IEventEmitter<Student> eventEmitter) {
        this.name = name;
        this.age = age;
        this.eventEmitter = eventEmitter;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public void update(String name, int age) {
        this.name = name;
        this.age = age;
        eventEmitter.emit(this);
    }
}
