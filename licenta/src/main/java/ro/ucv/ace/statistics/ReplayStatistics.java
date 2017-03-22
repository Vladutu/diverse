package ro.ucv.ace.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.ucv.ace.readability.Readability;
import ro.ucv.ace.readability.ReadabilityResult;
import ro.ucv.ace.repository.ReplayRepository;
import ro.ucv.ace.utils.BasicTextProcessor;
import ro.ucv.ace.utils.TextProcessor;

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

    @Autowired
    private Readability readability;

    public Map<String, List<Integer>> countWordsInReplaysGroupedByCategory() {
        Map<String, List<Integer>> wordCounter = new HashMap<>();

        replayRepository.getAll().forEach(replay -> {
            System.out.println("Computing replay with id " + replay.getId());
            String category = replay.getReview().getProduct().getCategory().getName();
            TextProcessor textProcessor = new BasicTextProcessor(replay.getBody());

            wordCounter.computeIfAbsent(category, k -> new ArrayList<>());
            wordCounter.get(category).add(textProcessor.numberOfWords());
        });

        return wordCounter;
    }

    public Map<String, Map<String, List<Double>>> computeLizabilityTestsGroupedByCategory() {
        Map<String, Map<String, List<Double>>> map = new HashMap<>();

        replayRepository.getAll().forEach(replay -> {
            System.out.println("Computing replay with id " + replay.getId());
            String category = replay.getReview().getProduct().getCategory().getName();
            map.computeIfAbsent(category, k -> {
                Map<String, List<Double>> value = new HashMap<>();
                value.put("Gunning Fog Index", new ArrayList<>());
                value.put("Automated Readability Index", new ArrayList<>());
                value.put("Flesch Reading Ease", new ArrayList<>());
                value.put("Flesch-Kincaid Grade Level", new ArrayList<>());
                value.put("Coleman Liau Index", new ArrayList<>());

                return value;
            });

            ReadabilityResult readabilityResult = readability.computeReadability(replay.getBody());
            map.get(category).get("Gunning Fog Index").add(readabilityResult.getGunningFogIndex());
            map.get(category).get("Automated Readability Index").add(readabilityResult.getAutomatedReadabilityIndex());
            map.get(category).get("Flesch Reading Ease").add(readabilityResult.getFleschReadingEase());
            map.get(category).get("Flesch-Kincaid Grade Level").add(readabilityResult.getFleschKincaidGradeLevel());
            map.get(category).get("Coleman Liau Index").add(readabilityResult.getColemanLiauIndex());
        });

        return map;
    }
}
