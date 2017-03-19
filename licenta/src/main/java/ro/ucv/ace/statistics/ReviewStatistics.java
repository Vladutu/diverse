package ro.ucv.ace.statistics;

import edu.stanford.nlp.simple.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.ucv.ace.repository.ReviewRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Geo on 18.03.2017.
 */
@Component
@Transactional(readOnly = true)
public class ReviewStatistics {

    @Autowired
    private ReviewRepository reviewRepository;

    public Map<String, List<Integer>> countWordsInReviewsGroupedByCategory() {
        Map<String, List<Integer>> wordCounter = new HashMap<>();

        reviewRepository.getAll().forEach(review -> {
            System.out.println("Computing review with id " + review.getId());
            Document document = new Document(review.getBody());
            String category = review.getProduct().getCategory().getName();
            wordCounter.computeIfAbsent(category, k -> new ArrayList<>());

            int count = 0;
            count += document.sentences().parallelStream()
                    .mapToInt(sentence -> sentence.words().size())
                    .sum();

            wordCounter.get(category).add(count);
        });

        return wordCounter;
    }
}
