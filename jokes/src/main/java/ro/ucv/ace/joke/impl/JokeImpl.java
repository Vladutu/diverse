package ro.ucv.ace.joke.impl;

import ro.ucv.ace.joke.Joke;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Geo on 01.11.2016.
 */
public class JokeImpl implements Joke {

    private String text;

    private Map<String, Integer> wordsMap = new HashMap<>();

    public JokeImpl(String text) {
        this.text = text;
        String[] words = text.replaceAll("[^a-zA-Z ]", " ").toLowerCase().split("\\s+");
        for (String word : words) {
            if (wordsMap.containsKey(word)) {
                wordsMap.replace(word, wordsMap.get(word) + 1);
            } else {
                wordsMap.put(word, 1);
            }
        }

    }

    @Override
    public double computeCosineSimilarity(Joke joke) {
        return joke.computeCosineSimilarity(wordsMap);
    }

    @Override
    public double computeCosineSimilarity(Map<String, Integer> otherJokeWords) {
        double abSum = 0;
        double aSquare = 0;
        double bSquare = 0;

        for (Map.Entry<String, Integer> entry : wordsMap.entrySet()) {
            if (otherJokeWords.containsKey(entry.getKey())) {
                abSum += (entry.getValue() * otherJokeWords.get(entry.getKey()));
            }

            aSquare += entry.getValue() * entry.getValue();
        }

        for (Map.Entry<String, Integer> entry : otherJokeWords.entrySet()) {
            bSquare += entry.getValue() * entry.getValue();
        }

        return Math.acos(abSum / (Math.sqrt(aSquare) * Math.sqrt(bSquare)));
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
