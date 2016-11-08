package ro.ucv.ace;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Geo on 08.11.2016.
 */
public class JokeImpl implements Joke {

    private SimilarityAlgorithm similarityAlgorithm;

    private String text;

    private Map<String, Integer> frequency = new HashMap<>();

    public JokeImpl(String text, SimilarityAlgorithm defaultSimilarityAlgorithm) {
        this.text = text;
        this.similarityAlgorithm = defaultSimilarityAlgorithm;
        parseText();
    }

    private void parseText() {
        String[] words = text.replaceAll("[^a-zA-Z ]", " ").toLowerCase().split("\\s+");
        for (String word : words) {
            if (word.length() > 1) {
                if (frequency.containsKey(word)) {
                    frequency.replace(word, frequency.get(word) + 1);
                } else {
                    frequency.put(word, 1);
                }
            }
        }

    }

    @Override
    public Double compare(Joke joke) {
        return similarityAlgorithm.compare(frequency, joke.getFrequency());
    }

    @Override
    public Map<String, Integer> getFrequency() {
        return frequency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JokeImpl joke = (JokeImpl) o;

        return text != null ? text.equals(joke.text) : joke.text == null;

    }

    @Override
    public int hashCode() {
        return text != null ? text.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Joke = {" + text + "}";
    }
}
