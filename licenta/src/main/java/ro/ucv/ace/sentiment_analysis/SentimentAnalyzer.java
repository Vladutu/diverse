package ro.ucv.ace.sentiment_analysis;

import ro.ucv.ace.sentiment_analysis.grammar.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Geo on 26.06.2017.
 */
//@Component
public class SentimentAnalyzer {

    private List<Intensifier> intensifiers;

    //@Autowired
    private GrammarParser grammarParser = new GrammarParser();

    private final static String NEGATION = "neg";

    public SentimentAnalyzer() {
        loadIntensifiers();
    }

    private void loadIntensifiers() {
        File file = new File(getClass().getClassLoader().getResource("intensifiers.txt").getFile());
        intensifiers = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(" ");
                intensifiers.add(new Intensifier(data[0], Integer.valueOf(data[1])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double computePolarity(String review) {
        double polarity = 0;
        Document document = grammarParser.parse(review);
        for (Sentence sentence : document.getSentences()) {
            List<Word> negations = determineNegations(sentence.getDependencies());
            List<Word> intensifiers = determineIntensifiers(sentence.getWords());

            int x = 3;
        }


        return polarity;
    }

    private List<Word> determineIntensifiers(List<Word> words) {
        List<Word> intensifierWords = new ArrayList<>();
        for (Word word : words) {
            for (Intensifier intensifier : intensifiers) {
                if (intensifier.getName().equals(word.getLemma())) {
                    intensifierWords.add(word);
                    break;
                }
            }
        }

        return intensifierWords;
    }

    private List<Word> determineNegations(List<Dependency> dependencies) {
        List<Word> negations = new ArrayList<>();
        for (Dependency dependency : dependencies) {
            if (dependency.getRelation().equals(NEGATION)) {
                negations.add(dependency.getDependent());
            }
        }

        return negations;
    }
}
