package ro.ucv.ace.sentiment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PolarityAnalyzer {

    private SentimentalPolarityAlgorithm sentimentalPolarityAlgorithm;

    @Autowired
    public PolarityAnalyzer(SentimentalPolarityAlgorithm sentimentalPolarityAlgorithm) {
        this.sentimentalPolarityAlgorithm = sentimentalPolarityAlgorithm;
    }

    public Polarity computePolarity(String text) {
        List<Double> polarities = sentimentalPolarityAlgorithm.execute(text);
        return computeOverallPolarity(polarities);
    }

    public static Polarity computeOverallPolarity(List<Double> polarities) {
        long positivePolarities = polarities.stream()
                .filter(polarity -> polarity > 0)
                .count();
        long negativePolarities = polarities.stream()
                .filter(polarity -> polarity <= 0)
                .count();

        if (positivePolarities > negativePolarities) {
            return Polarity.POSITIVE;
        }

        return Polarity.NEGATIVE;
    }

    public enum Polarity {

        POSITIVE, NEGATIVE
    }
}
