package ro.ucv.ace.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.ucv.ace.entity.Review;
import ro.ucv.ace.readability.Readability;
import ro.ucv.ace.readability.ReadabilityResult;
import ro.ucv.ace.repository.ReviewRepository;
import ro.ucv.ace.sentiment_analysis.SentimentAnalyzer;
import ro.ucv.ace.utils.BasicTextProcessor;
import ro.ucv.ace.utils.TextProcessor;

import java.util.*;

/**
 * Created by Geo on 18.03.2017.
 */
@Component
@Transactional(readOnly = true)
public class ReviewStatistics {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private Readability readability;

    @Autowired
    private SentimentAnalyzer sentimentAnalyzer;

    public Map<String, List<Integer>> countWordsInReviewsGroupedByCategory() {
        Map<String, List<Integer>> wordCounter = new HashMap<>();

        reviewRepository.getAll().forEach(review -> {
            System.out.println("Computing review with id " + review.getId());
            String category = review.getProduct().getCategory().getName();
            TextProcessor textProcessor = new BasicTextProcessor(review.getBody());

            wordCounter.computeIfAbsent(category, k -> new ArrayList<>());
            wordCounter.get(category).add(textProcessor.numberOfWords());
        });

        return wordCounter;
    }

    public Map<String, Map<String, List<Double>>> computeLizabilityTestsGroupedByCategory() {
        Map<String, Map<String, List<Double>>> map = new HashMap<>();

        reviewRepository.getAll().forEach(review -> {
            System.out.println("Computing review with id " + review.getId());
            String category = review.getProduct().getCategory().getName();
            map.computeIfAbsent(category, k -> {
                Map<String, List<Double>> value = new HashMap<>();
                value.put("Gunning Fog Index", new ArrayList<>());
                value.put("Automated Readability Index", new ArrayList<>());
                value.put("Flesch Reading Ease", new ArrayList<>());
                value.put("Flesch-Kincaid Grade Level", new ArrayList<>());
                value.put("Coleman Liau Index", new ArrayList<>());

                return value;
            });

            ReadabilityResult readabilityResult = readability.computeReadability(review.getBody());
            map.get(category).get("Gunning Fog Index").add(readabilityResult.getGunningFogIndex());
            map.get(category).get("Automated Readability Index").add(readabilityResult.getAutomatedReadabilityIndex());
            map.get(category).get("Flesch Reading Ease").add(readabilityResult.getFleschReadingEase());
            map.get(category).get("Flesch-Kincaid Grade Level").add(readabilityResult.getFleschKincaidGradeLevel());
            map.get(category).get("Coleman Liau Index").add(readabilityResult.getColemanLiauIndex());

        });

        return map;
    }

    public Map<String, List<Integer>> countWordsAndUsefulness() {
        Map<String, List<Integer>> map = new HashMap<>();
        map.put("words", new ArrayList<>());
        map.put("helpful_votes", new ArrayList<>());

        reviewRepository.getAll().forEach(review -> {
            System.out.println("Computing review with id " + review.getId());
            TextProcessor textProcessor = new BasicTextProcessor(review.getBody());
            int words = textProcessor.numberOfWords();
            int helpfulVotes = review.getHelpfulVotes();

            map.get("words").add(words);
            map.get("helpful_votes").add(helpfulVotes);
        });

        return map;
    }


    public Map<String, List<Double>> helpfulVotesAndAIR() {
        Map<String, List<Double>> map = new HashMap<>();
        map.put("helpful_votes", new ArrayList<>());
        map.put("air", new ArrayList<>());

        reviewRepository.getAll().forEach(review -> {
            System.out.println("Computing review with id " + review.getId());
            ReadabilityResult readabilityResult = readability.computeReadability(review.getBody());
            double helpfulVotes = review.getHelpfulVotes();
            double air = readabilityResult.getAutomatedReadabilityIndex();

            map.get("helpful_votes").add(helpfulVotes);
            map.get("air").add(air);
        });

        return map;
    }

    public Map<Integer, List<Double>> ratingAndSentimentAnalysis() {
        Map<Integer, List<Double>> map = new HashMap<>();

        Page<Review> reviews = reviewRepository.findAll
                (new PageRequest(1, 1000));

        for (Review review : reviews.getContent()) {
            System.out.println("Computing review with id " + review.getId());
            double polarity = sentimentAnalyzer.computePolarity(review.getBody());
            map.put(review.getId(), Arrays.asList(review.getProductRating(), polarity));
        }

        return map;
    }
}
