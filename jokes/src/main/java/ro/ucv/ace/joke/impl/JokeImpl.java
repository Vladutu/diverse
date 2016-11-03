package ro.ucv.ace.joke.impl;

import ro.ucv.ace.joke.Joke;
import ro.ucv.ace.joke.SimilarityAlgorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Geo on 01.11.2016.
 */
public class JokeImpl implements Joke {

    private String text;

    private Map<String, Integer> wordsMap = new HashMap<>();

    private SimilarityAlgorithm similarityAlgorithm;

    public JokeImpl(String text, SimilarityAlgorithm similarityAlgorithm) {
        this.similarityAlgorithm = similarityAlgorithm;
        this.text = text;

        splitTextInWords(text);
    }

    private void splitTextInWords(String text) {
        String[] words = text.replaceAll("[^a-zA-Z ]", " ").toLowerCase().split("\\s+");
        for (String word : words) {
            if (word.length() > 1) {
                if (wordsMap.containsKey(word)) {
                    wordsMap.replace(word, wordsMap.get(word) + 1);
                } else {
                    wordsMap.put(word, 1);
                }
            }
        }
    }

    @Override
    public double computeCosineSimilarity(Joke joke) {
        return joke.computeCosineSimilarity(wordsMap);
    }

    @Override
    public double computeCosineSimilarity(Map<String, Integer> otherJokeWords) {
        return similarityAlgorithm.computeSimilarity(wordsMap, otherJokeWords);
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
        return "Joke{" +
                "text='" + text + '\'' +
                '}';
    }


}
