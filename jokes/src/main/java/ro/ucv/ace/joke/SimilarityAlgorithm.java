package ro.ucv.ace.joke;

import java.util.Map;

/**
 * Created by Geo on 03.11.2016.
 */
public interface SimilarityAlgorithm {

    double computeSimilarity(Map<String, Integer> toBeCompared, Map<String, Integer> other);
}
