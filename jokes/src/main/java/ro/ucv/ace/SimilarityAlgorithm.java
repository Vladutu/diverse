package ro.ucv.ace;

import java.util.Map;

/**
 * Created by Geo on 08.11.2016.
 */
public interface SimilarityAlgorithm {
    Double compare(Map<String, Integer> oneFrequency, Map<String, Integer> otherFrequency);
}
