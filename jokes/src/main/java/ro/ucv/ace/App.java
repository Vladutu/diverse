package ro.ucv.ace;

import ro.ucv.ace.joke.Comparator;
import ro.ucv.ace.joke.Joke;
import ro.ucv.ace.joke.JokeParser;
import ro.ucv.ace.joke.JokeRepository;
import ro.ucv.ace.joke.impl.CosineComparator;
import ro.ucv.ace.joke.impl.JokeBuilderImpl;
import ro.ucv.ace.joke.impl.JokeParseImpl;
import ro.ucv.ace.joke.impl.JokeRepositoryImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        JokeParser jokeParser = new JokeParseImpl(new JokeBuilderImpl());
        List<Joke> jokes = new ArrayList<>();

        String folderName = "jokes/";
        String filePrefix = "init";
        String extension = ".html";

        for (int i = 1; i <= 100; i++) {
            File file = new File(App.class.getClassLoader().getResource(folderName + filePrefix + i + extension).getFile());
            Joke joke = jokeParser.parse(file);
            jokes.add(joke);
        }

        JokeRepository jokeRepository = new JokeRepositoryImpl(jokes);
        Comparator cosineComparator = new CosineComparator(jokes.get(0));

        jokeRepository.findSimilarJokes(cosineComparator).forEach(System.out::println);
    }
}
