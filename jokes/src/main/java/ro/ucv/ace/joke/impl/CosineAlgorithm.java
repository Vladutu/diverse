package ro.ucv.ace.joke.impl;

import ro.ucv.ace.joke.SimilarityAlgorithm;

import java.util.Map;

/**
 * Created by Geo on 03.11.2016.
 */
public class CosineAlgorithm implements SimilarityAlgorithm {

    @Override
    public double computeSimilarity(Map<String, Integer> toBeCompared, Map<String, Integer> other) {
        double abSum = 0;
        double aSquare = 0;
        double bSquare = 0;

        for (Map.Entry<String, Integer> entry : toBeCompared.entrySet()) {
            if (other.containsKey(entry.getKey())) {
                abSum += (entry.getValue() * other.get(entry.getKey()));
            }

            aSquare += entry.getValue() * entry.getValue();
        }

        for (Map.Entry<String, Integer> entry : other.entrySet()) {
            bSquare += entry.getValue() * entry.getValue();
        }

        return Math.acos(abSum / (Math.sqrt(aSquare) * Math.sqrt(bSquare)));
    }
}
