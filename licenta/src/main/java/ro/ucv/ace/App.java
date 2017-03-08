package ro.ucv.ace;

import ro.ucv.ace.repository.SenticRepository;
import ro.ucv.ace.repository.SenticRepositoryImpl;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        SenticRepository senticRepository = new SenticRepositoryImpl();
        System.out.println(senticRepository.findConcept("love"));
    }
}
