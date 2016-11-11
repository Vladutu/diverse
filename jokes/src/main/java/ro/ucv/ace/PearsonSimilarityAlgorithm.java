package ro.ucv.ace;

import java.util.Map;

/**
 * Created by Geo on 10.11.2016.
 */
public class PearsonSimilarityAlgorithm implements SimilarityAlgorithm {

    @Override
    public Double compare(Map<String, Integer> oneFrequency, Map<String, Integer> otherFrequency) {
        double xySum = 0;
        double xSquare = 0;
        double ySquare = 0;
        double xMean = 0;
        double yMean = 0;
        int n = 0;

        for (Map.Entry<String, Integer> entry : oneFrequency.entrySet()) {
            if (otherFrequency.containsKey(entry.getKey())) {
                xySum += (entry.getValue() * otherFrequency.get(entry.getKey()));
            }

            xSquare += entry.getValue() * entry.getValue();
        }

        for (Map.Entry<String, Integer> entry : otherFrequency.entrySet()) {
            ySquare += entry.getValue() * entry.getValue();
        }

        for (String key : oneFrequency.keySet()) {
            if (!otherFrequency.containsKey(key)) {
                otherFrequency.put(key, 0);
            }
        }

        for (String key : otherFrequency.keySet()) {
            if (!oneFrequency.containsKey(key)) {
                oneFrequency.put(key, 0);
            }
        }

        n = oneFrequency.entrySet().size();

        for (Integer value : oneFrequency.values()) {
            xMean += value;
        }
        xMean /= n;

        for (Integer value : oneFrequency.values()) {
            yMean += value;
        }
        yMean /= n;

        return (xySum - n * xMean * yMean) / (Math.sqrt(xSquare - n * xMean * xMean) * Math.sqrt(ySquare - n * yMean * yMean));
    }
}
