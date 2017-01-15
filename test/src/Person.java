/**
 * Created by Geo on 07.01.2017.
 */
public class Person {

    private String name;

    private Integer age;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public void printOnConsole() {
        System.out.println("My name is " + name + " and I'm " + age + " years old");
    }
}
