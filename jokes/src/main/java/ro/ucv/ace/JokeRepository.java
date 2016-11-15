package ro.ucv.ace;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Geo on 08.11.2016.
 */
public class JokeRepository {

    private List<Joke> jokes;

    public JokeRepository(List<Joke> jokes) {
        this.jokes = jokes;
    }

    public List<Map.Entry<Joke, Double>> findSimilarJokes(Joke jokeToCompare) {
        Map<Joke, Double> similarityScores = computeSimilarityScores(jokeToCompare);

        return selectMostSimilarJokes(similarityScores);
    }

    private Map<Joke, Double> computeSimilarityScores(Joke jokeToCompare) {
        Map<Joke, Double> similarityScores = new HashMap<>();
        jokes.forEach(joke -> {
            Double score = jokeToCompare.compare(joke);
            similarityScores.put(joke, score);
        });

        return similarityScores;
    }


    private List<Map.Entry<Joke, Double>> selectMostSimilarJokes(Map<Joke, Double> similarityScores) {
        return similarityScores.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .collect(Collectors.toList())
                .subList(1, 4);
    }
}
