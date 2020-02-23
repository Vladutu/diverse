package ro.ucv.ace.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import ro.ucv.ace.review.Review;
import ro.ucv.ace.review.ReviewRepository;
import ro.ucv.ace.sentiment.SentimentalPolarityAlgorithm;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ReviewTester {

    private final SentimentalPolarityAlgorithm sentimentalPolarityAlgorithm;
    private final ReviewRepository reviewRepository;
    private static final String PATH = "D:\\reviews.json";
    private ObjectMapper mapper = new ObjectMapper();
    private ExecutorService executorService = Executors.newFixedThreadPool(4);

    @Autowired
    public ReviewTester(SentimentalPolarityAlgorithm sentimentalPolarityAlgorithm, ReviewRepository reviewRepository) {
        this.sentimentalPolarityAlgorithm = sentimentalPolarityAlgorithm;
        this.reviewRepository = reviewRepository;
    }

    @SneakyThrows
    public void testReviews(int pageNumber, int size) {
        Page<Review> page = null;

        do {
            log.info("Requesting page {}", pageNumber);
            page = reviewRepository.findAll(PageRequest.of(pageNumber, size));

            Map<Integer, Pair<Integer, List<Double>>> map = readMap();
            //computeResultAndAddToMap(page, map);
            List<Pair<Review, List<Double>>> pairs = computeResult(page);
            pairs.forEach(pair -> addToMap(pair, map));


            log.info("Writing result to disk");
            writeMap(map);
            log.info("Finished writing to disk");

            pageNumber++;
            System.gc();
        } while (page.hasContent());
    }

    @SneakyThrows
    private List<Pair<Review, List<Double>>> computeResult(Page<Review> page) {
        List<ReviewCallable> callables = page.getContent().stream()
                .filter(review -> !StringUtils.isEmpty(review.getBody()))
                .filter(review -> review.getProductRating() != 3)
                .filter(review -> review.getBody().split(" ").length <= 150)
                .map(review -> new ReviewCallable(sentimentalPolarityAlgorithm, review, page.getNumber()))
                .collect(Collectors.toList());

        List<Future<Pair<Review, List<Double>>>> futures = executorService.invokeAll(callables);

        return futures.stream()
                .map(this::get)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private <T> T get(Future<T> future) {
        return future.get();
    }

    private void addToMap(Pair<Review, List<Double>> pair, Map<Integer, Pair<Integer, List<Double>>> map) {
        map.put(pair.getLeft().getId(), Pair.of(pair.getLeft().getProductRating(), pair.getRight()));
    }

    @SneakyThrows
    private Map<Integer, Pair<Integer, List<Double>>> readMap() {
        String mapAsString = new String(Files.readAllBytes(Paths.get(PATH)));
        TypeReference<Map<Integer, Pair<Integer, List<Double>>>> ref = new TypeReference<Map<Integer, Pair<Integer, List<Double>>>>() {
        };

        return mapper.readValue(mapAsString, ref);
    }

    @SneakyThrows
    private void writeMap(Map<Integer, Pair<Integer, List<Double>>> map) {
        String mapAsString = mapper.writeValueAsString(map);
        Files.write(Paths.get(PATH), mapAsString.getBytes());
    }

}
