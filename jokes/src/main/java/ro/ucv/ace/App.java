package ro.ucv.ace;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        JokeParser jokeParser = new JokeParser(new JokeBuilder(new PearsonSimilarityAlgorithm()));

        Joke joke1 = new JokeImpl("Ce mai faci", new PearsonSimilarityAlgorithm());
        Joke joke2 = new JokeImpl("Ana are mere", new PearsonSimilarityAlgorithm());
        Joke joke3 = new JokeImpl("Gluma fara sens", new PearsonSimilarityAlgorithm());
        Joke joke4 = new JokeImpl("Bulervardul bibescu este imens", new PearsonSimilarityAlgorithm());
        Joke joke5 = new JokeImpl("Am condus la facultate", new PearsonSimilarityAlgorithm());

        List<Joke> j = new ArrayList<>();
        j.add(joke1);
        j.add(joke2);
        j.add(joke3);
        j.add(joke4);
        j.add(joke5);

        List<Joke> jokes = new ArrayList<>();

        String folderName = "jokes/";
        String filePrefix = "init";
        String extension = ".html";

        for (int i = 1; i <= 100; i++) {
            File file = new File(App.class.getClassLoader().getResource(folderName + filePrefix + i + extension).getFile());
            Joke joke = jokeParser.parse(file);
            jokes.add(joke);
        }

        JokeRepository jokeRepository = new JokeRepository(j);

        jokeRepository.findSimilarJokes(jokes.get(0)).forEach(System.out::println);
    }
}
