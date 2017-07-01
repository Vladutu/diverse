package ro.ucv.ace.sentiment_analysis.sentiwordnet;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Geo on 25.06.2017.
 */
@Component
public class SentiWordNet {

    private Map<String, Double> dictionary;

    public SentiWordNet() {
        loadSenti();
    }

    public double extract(String word, String pos) {
        Double polarity = dictionary.get(word + "#" + pos);
        if (polarity == null) {
            return 0;
        }

        return polarity;
    }

    private void loadSenti() {
        File sentiFile = new File(getClass().getClassLoader().getResource("sentiwordnet.txt").getFile());
        // This is our main dictionary representation
        dictionary = new HashMap<String, Double>();
        // From String to list of doubles.
        HashMap<String, HashMap<Integer, Double>> tempDictionary = new HashMap<>();

        try (Scanner scanner = new Scanner(sentiFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().startsWith("#")) {
                    continue;
                }

                String[] data = line.split("\t");
                String wordTypeMarker = data[0];

                // Calculate synset score as score = PosS - NegS
                Double synsetScore = Double.parseDouble(data[2])
                        - Double.parseDouble(data[3]);

                // Get all Synset terms
                String[] synTermsSplit = data[4].split(" ");

                // Go through all terms of current synset.
                for (String synTermSplit : synTermsSplit) {
                    // Get synterm and synterm rank
                    String[] synTermAndRank = synTermSplit.split("#");
                    String synTerm = synTermAndRank[0] + "#"
                            + wordTypeMarker;

                    int synTermRank = Integer.parseInt(synTermAndRank[1]);
                    // What we get here is a map of the type:
                    // term -> {score of synset#1, score of synset#2...}

                    // Add map to term if it doesn't have one
                    if (!tempDictionary.containsKey(synTerm)) {
                        tempDictionary.put(synTerm,
                                new HashMap<Integer, Double>());
                    }

                    // Add synset link to synterm
                    tempDictionary.get(synTerm).put(synTermRank,
                            synsetScore);
                }
            }

            // Go through all the terms.
            for (Map.Entry<String, HashMap<Integer, Double>> entry : tempDictionary
                    .entrySet()) {
                String word = entry.getKey();
                Map<Integer, Double> synSetScoreMap = entry.getValue();

                // Calculate weighted average. Weigh the synsets according to
                // their rank.
                // Score= 1/2*first + 1/3*second + 1/4*third ..... etc.
                // Sum = 1/1 + 1/2 + 1/3 ...
                double score = 0.0;
                double sum = 0.0;
                for (Map.Entry<Integer, Double> setScore : synSetScoreMap
                        .entrySet()) {
                    score += setScore.getValue() / (double) setScore.getKey();
                    sum += 1.0 / (double) setScore.getKey();
                }
                score /= sum;

                dictionary.put(word, score);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
