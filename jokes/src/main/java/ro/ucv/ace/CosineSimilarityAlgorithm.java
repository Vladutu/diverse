package ro.ucv.ace;

import java.util.Map;

/**
 * Created by Geo on 08.11.2016.
 */
public class CosineSimilarityAlgorithm implements SimilarityAlgorithm {

    @Override
    public Double compare(Map<String, Integer> oneFrequency, Map<String, Integer> otherFrequency) {
        double abSum = 0;
        double aSquare = 0;
        double bSquare = 0;

        for (Map.Entry<String, Integer> entry : oneFrequency.entrySet()) {
            if (otherFrequency.containsKey(entry.getKey())) {
                abSum += (entry.getValue() * otherFrequency.get(entry.getKey()));
            }

            aSquare += entry.getValue() * entry.getValue();
        }

        for (Map.Entry<String, Integer> entry : otherFrequency.entrySet()) {
            bSquare += entry.getValue() * entry.getValue();
        }

        return Math.acos(abSum / (Math.sqrt(aSquare) * Math.sqrt(bSquare)));
    }
}
