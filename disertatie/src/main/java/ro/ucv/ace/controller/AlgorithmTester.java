package ro.ucv.ace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ucv.ace.parser.MultiSentenceException;
import ro.ucv.ace.sentiment.SentimentalPolarityAlgorithm;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class AlgorithmTester {

    private final SentimentalPolarityAlgorithm sentimentalPolarityAlgorithm;

    @Autowired
    public AlgorithmTester(SentimentalPolarityAlgorithm sentimentalPolarityAlgorithm) {
        this.sentimentalPolarityAlgorithm = sentimentalPolarityAlgorithm;
    }

    public void test() {
        Map<Boolean, List<Double>> negativeResult = executeTests("negative.txt", (value) -> value < 0);
        System.out.println("----------------------------------------------------------------------------------------");
        Map<Boolean, List<Double>> positiveResult = executeTests("positive.txt", (value) -> value > 0);

        System.out.println("---------------------------------------RESULTS-----------------------------------------");
        System.out.println("Negative total: " + Math.addExact(negativeResult.get(false).size(), negativeResult.get(true).size()));
        System.out.println("Negative correct: " + negativeResult.get(true).size());
        System.out.println("Positive total: " + Math.addExact(positiveResult.get(false).size(), positiveResult.get(true).size()));
        System.out.println("Positive correct: " + positiveResult.get(true).size());
    }

    private Map<Boolean, List<Double>> executeTests(String fileName, Function<Double, Boolean> function) {
        List<String> toSave = new ArrayList<>();
        toSave.add("--------------------------------------------------");
        Map<Boolean, List<Double>> res = readFile(fileName)
                .map(sentence -> {
                    try {
                        Double result = sentimentalPolarityAlgorithm.execute(sentence);
                        String toBeSaved = sentence + "     " + result;
                        System.out.println(toBeSaved);
                        toSave.add(toBeSaved);
                        return result;
                    } catch (MultiSentenceException e) {
                        return -100.0;
                    }
                })
                .filter(value -> value != -100.0)
                .collect(Collectors.groupingBy(function));
        toSave.add("--------------------RESULTS------------------------------");
        toSave.add("TOTAL:" + Math.addExact(res.get(false).size(), res.get(true).size()));
        toSave.add("CORRECT: " + res.get(true).size());

        try {
            Files.write(Paths.get("D:\\" + fileName), toSave);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static Stream<String> readFile(String fileName) {
        try {
            URI uri = AlgorithmTester.class.getClassLoader().getResource(fileName).toURI();

            return Files.lines(Paths.get(uri));
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
