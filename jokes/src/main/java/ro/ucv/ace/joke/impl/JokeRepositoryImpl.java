package ro.ucv.ace.joke.impl;

import ro.ucv.ace.joke.Comparator;
import ro.ucv.ace.joke.Joke;
import ro.ucv.ace.joke.JokeRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Geo on 01.11.2016.
 */
public class JokeRepositoryImpl implements JokeRepository {

    private List<Joke> jokes;

    public JokeRepositoryImpl(List<Joke> jokes) {
        this.jokes = jokes;
    }

    @Override
    public List<Joke> findSimilarJokes(Comparator comparator) {
        Map<Joke, Double> similarityScore = createSimilartyScoreMap(comparator);
        List<Map.Entry<Joke, Double>> sortedEntry = sortSimilarityMap(similarityScore);

        return findTopSimilarJokes(sortedEntry);
    }

    private List<Joke> findTopSimilarJokes(List<Map.Entry<Joke, Double>> sortedEntry) {
        List<Joke> similarTopJokes = new ArrayList<>();
        for (int i = 0; i <= 3; i++) {
            similarTopJokes.add(sortedEntry.get(i).getKey());
        }
        return similarTopJokes;
    }

    private Map<Joke, Double> createSimilartyScoreMap(Comparator comparator) {
        Map<Joke, Double> similarityScore = new HashMap<>();
        for (Joke joke : jokes) {
            similarityScore.put(joke, comparator.compare(joke));
        }
        return similarityScore;
    }

    private List<Map.Entry<Joke, Double>> sortSimilarityMap(Map<Joke, Double> similarityScore) {
        return similarityScore.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toList());
    }
}
