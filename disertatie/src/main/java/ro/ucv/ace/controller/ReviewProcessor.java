package ro.ucv.ace.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ReviewProcessor {

    private static final String PATH = "D:\\reviews.json";
    private ObjectMapper mapper = new ObjectMapper();

    public void process() {
        Map<Integer, Pair<Integer, List<Double>>> reviewResultMap = readMap();

        List<Pair<Integer, Polarity>> starPolarityPairList = reviewResultMap.values().stream()
                .map(integerListPair -> Pair.of(integerListPair.getLeft(), computeOverallPolarity(integerListPair.getRight())))
                .collect(Collectors.toList());

        List<Pair<Integer, Polarity>> positiveStarPolarityPairList = starPolarityPairList.stream().filter(pair -> pair.getLeft() > 3).collect(Collectors.toList());
        List<Pair<Integer, Polarity>> negativeStarPolarityPairList = starPolarityPairList.stream().filter(pair -> pair.getLeft() < 3).collect(Collectors.toList());

        long correctPositive = positiveStarPolarityPairList.stream()
                .filter(pair -> pair.getRight().equals(Polarity.POSITIVE))
                .count();
        long correctNegative = negativeStarPolarityPairList.stream()
                .filter(pair -> pair.getRight().equals(Polarity.NEGATIVE))
                .count();

        log.info("No positive reviews {}. Correct positive {}. Accuracy {}", positiveStarPolarityPairList.size(), correctPositive, ((double) correctPositive / positiveStarPolarityPairList.size()));
        log.info("No negative reviews {}. Correct negative {}. Accuracy {}", negativeStarPolarityPairList.size(), correctNegative, ((double) correctNegative / negativeStarPolarityPairList.size()));

    }

    @SneakyThrows
    private Map<Integer, Pair<Integer, List<Double>>> readMap() {
        String mapAsString = new String(Files.readAllBytes(Paths.get(PATH)));
        TypeReference<Map<Integer, Pair<Integer, List<Double>>>> ref = new TypeReference<Map<Integer, Pair<Integer, List<Double>>>>() {
        };

        return mapper.readValue(mapAsString, ref);
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
