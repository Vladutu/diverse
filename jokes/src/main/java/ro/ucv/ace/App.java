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
        List<Joke> jokes = new ArrayList<>();

        String folderName = "jokes/";
        String filePrefix = "init";
        String extension = ".html";

        for (int i = 1; i <= 100; i++) {
            File file = new File(App.class.getClassLoader().getResource(folderName + filePrefix + i + extension).getFile());
            Joke joke = jokeParser.parse(file);
            jokes.add(joke);
        }

        JokeRepository jokeRepository = new JokeRepository(jokes);

        jokeRepository.findSimilarJokes(jokes.get(0)).forEach(System.out::println);
    }
}
