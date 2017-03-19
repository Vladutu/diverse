package ro.ucv.ace.statistics;

import edu.stanford.nlp.simple.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.ucv.ace.repository.ReplayRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Geo on 18.03.2017.
 */
@Component
@Transactional(readOnly = true)
public class ReplayStatistics {
    @Autowired
    private ReplayRepository replayRepository;

    public Map<String, List<Integer>> countWordsInReplaysGroupedByCategory() {
        Map<String, List<Integer>> wordCounter = new HashMap<>();

        replayRepository.getAll().forEach(replay -> {
            System.out.println("Computing replay with id " + replay.getId());
            Document document = new Document(replay.getBody());
            String category = replay.getReview().getProduct().getCategory().getName();
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
