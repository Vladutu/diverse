package ro.ucv.ace.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ucv.ace.sentiment.SentimentalPolarityAlgorithm;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ro.ucv.ace.controller.ReviewProcessor.Polarity;
import static ro.ucv.ace.controller.ReviewProcessor.computeOverallPolarity;

@Component
public class AlgorithmTester {

    private final SentimentalPolarityAlgorithm sentimentalPolarityAlgorithm;
    private ExecutorService executorService = Executors.newFixedThreadPool(3);

    @Autowired
    public AlgorithmTester(SentimentalPolarityAlgorithm sentimentalPolarityAlgorithm) {
        this.sentimentalPolarityAlgorithm = sentimentalPolarityAlgorithm;
    }

    public void test(String type) {
        String posName = "";
        String negName = "";
        if (type.equals("Kotzias")) {
            posName = "positive.txt";
            negName = "negative.txt";
        }
        if (type.equals("Pang")) {
            posName = "rt-polarity.pos";
            negName = "rt-polarity.neg";
        }

        Map<Boolean, List<Polarity>> negativeResult = executeTests(negName, (polarity) -> polarity.equals(Polarity.NEGATIVE));
        System.out.println("----------------------------------------------------------------------------------------");
        Map<Boolean, List<Polarity>> positiveResult = executeTests(posName, (polarity) -> polarity.equals(Polarity.POSITIVE));

        System.out.println("---------------------------------------RESULTS-----------------------------------------");
        System.out.println("Negative total: " + Math.addExact(negativeResult.get(false).size(), negativeResult.get(true).size()));
        System.out.println("Negative correct: " + negativeResult.get(true).size());
        System.out.println("Positive total: " + Math.addExact(positiveResult.get(false).size(), positiveResult.get(true).size()));
        System.out.println("Positive correct: " + positiveResult.get(true).size());
    }

    @SneakyThrows
    private Map<Boolean, List<Polarity>> executeTests(String fileName, Function<Polarity, Boolean> function) {
        List<String> toSave = new ArrayList<>();
        toSave.add("--------------------------------------------------");
        List<Callable<Pair<String, List<Double>>>> callables = readFile(fileName)
                .map(sentence -> (Callable<Pair<String, List<Double>>>) () -> {
                    System.out.println(sentence);
                    return Pair.of(sentence, sentimentalPolarityAlgorithm.execute(sentence));
                })
                .collect(Collectors.toList());
        List<Future<Pair<String, List<Double>>>> futures = executorService.invokeAll(callables);

        Map<Boolean, List<Polarity>> res = futures.stream()
                .map(this::get)
                .map(pair -> {
                    Polarity polarity = computeOverallPolarity(pair.getRight());
                    String toBeSaved = pair.getLeft() + "     " + polarity;
                    System.out.println(toBeSaved);
                    toSave.add(toBeSaved);

                    return polarity;
                })
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

    @SneakyThrows
    private <T> T get(Future<T> future) {
        return future.get();
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
