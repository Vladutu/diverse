package ro.ucv.ace.controller;

import lombok.extern.slf4j.Slf4j;
import ro.ucv.ace.review.Review;
import ro.ucv.ace.sentiment.SentimentalPolarityAlgorithm;

import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
public class ReviewCallable implements Callable<Pair<Review, List<Double>>> {

    private SentimentalPolarityAlgorithm sentimentalPolarityAlgorithm;
    private Review review;
    private int pageNumber;

    public ReviewCallable(SentimentalPolarityAlgorithm sentimentalPolarityAlgorithm, Review review, int pageNumber) {
        this.sentimentalPolarityAlgorithm = sentimentalPolarityAlgorithm;
        this.review = review;
        this.pageNumber = pageNumber;
    }

    @Override
    public Pair<Review, List<Double>> call() throws Exception {
        log.info("Page: {} Review id: {}", pageNumber, review.getId());
        return Pair.of(review, sentimentalPolarityAlgorithm.execute(review.getBody()));
    }
}
