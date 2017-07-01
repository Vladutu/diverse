package ro.ucv.ace.sentiment_analysis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ucv.ace.sentiment_analysis.grammar.*;
import ro.ucv.ace.sentiment_analysis.sentiwordnet.SentiWordNet;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Geo on 26.06.2017.
 */
@Component
public class SentimentAnalyzer {

    private List<Intensifier> intensifiers;

    @Autowired
    private GrammarParser grammarParser = new GrammarParser();

    @Autowired
    private SentiWordNet sentiWordNet = new SentiWordNet();

    private List<String> acceptedPos;

    private Map<String, String> posConversionMap;

    private final static String NEGATION = "neg";

    public SentimentAnalyzer() {
        loadIntensifiers();
        populateAcceptedPos();
        populatePosConversionMap();
    }

    private void populateAcceptedPos() {
        acceptedPos = Arrays.asList("NN", "NNS", "NNP", "NNPS", "JJ", "JJR", "JJS", "VB", "VBD", "VBG", "VBN",
                "VBP", "VBZ", "RB", "RBR", "RBS");
    }

    private void populatePosConversionMap() {
        posConversionMap = new HashMap<>();
        posConversionMap.put("NN", "n");
        posConversionMap.put("NNS", "n");
        posConversionMap.put("NNP", "n");
        posConversionMap.put("NNPS", "n");
        posConversionMap.put("JJ", "a");
        posConversionMap.put("JJR", "a");
        posConversionMap.put("JJS", "a");
        posConversionMap.put("VB", "v");
        posConversionMap.put("VBD", "v");
        posConversionMap.put("VBG", "v");
        posConversionMap.put("VBN", "v");
        posConversionMap.put("VBP", "v");
        posConversionMap.put("VBZ", "v");
        posConversionMap.put("RB", "r");
        posConversionMap.put("RBR", "r");
        posConversionMap.put("RBS", "r");

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

//    public double computePolarity(String review) {
//        double polarity = 0;
//
//        Document document = grammarParser.parse(review.toLowerCase());
//        for (Sentence sentence : document.getSentences()) {
//            List<Word> negations = determineNegations(sentence.getDependencies());
//            List<Word> intensifiers = determineIntensifiers(sentence.getWords());
//            List<Word> excludes = Stream.of(negations, intensifiers).flatMap(List::stream).collect(Collectors.toList());
//
//            for (Word word : sentence.getWords()) {
//                if (excludes.contains(word) || !acceptedPos.contains(word.getPos())) {
//                    continue;
//                }
//
//                int neg = computeNegation(word, sentence.getDependencies());
//                int intensifier = computeIntensifier(word, sentence.getDependencies());
//                double wordPolarity = computeWordPolarity(word);
//                int inverse = 0;
//                if (neg == 0) {
//                    inverse = computePrevWordsDependencyInversion(word, sentence.getDependencies());
//                }
//
//                double finalWordPolarity = ((double) intensifier / 100 * wordPolarity + wordPolarity)
//                        * Math.pow(-1, neg) * Math.pow(-1, inverse);
//
//                polarity += finalWordPolarity;
//            }
//        }
//
//        if (polarity > 1) {
//            polarity = 1;
//        }
//        if (polarity < -1) {
//            polarity = -1;
//        }
//
//        return polarity;
//    }

    public double computePolarity(String review) {
        double polarity = 0;
        List<Double> maxPol = new ArrayList<>();
        List<Double> minPol = new ArrayList<>();

        Document document = grammarParser.parse(review.toLowerCase());
        for (Sentence sentence : document.getSentences()) {
            List<Word> negations = determineNegations(sentence.getDependencies());
            List<Word> intensifiers = determineIntensifiers(sentence.getWords());
            List<Word> excludes = Stream.of(negations, intensifiers).flatMap(List::stream).collect(Collectors.toList());

            double maxPolWord = -5;
            double minPolWord = 5;

            for (Word word : sentence.getWords()) {
                if (excludes.contains(word) || !acceptedPos.contains(word.getPos())) {
                    continue;
                }

                int neg = computeNegation(word, sentence.getDependencies());
                int intensifier = computeIntensifier(word, sentence.getDependencies());
                double wordPolarity = computeWordPolarity(word);
                int inverse = 0;
                if (neg == 0) {
                    inverse = computePrevWordsDependencyInversion(word, sentence.getDependencies());
                }
                double finalWordPolarity = ((double) intensifier / 100 * wordPolarity + wordPolarity)
                        * Math.pow(-1, neg) * Math.pow(-1, inverse);

                if (finalWordPolarity > maxPolWord) {
                    maxPolWord = finalWordPolarity;
                }
                if (finalWordPolarity < minPolWord) {
                    minPolWord = finalWordPolarity;
                }
            }
            maxPol.add(maxPolWord);
            minPol.add(minPolWord);
        }
        double maxPolAvg = maxPol.stream().mapToDouble(d -> d).sum() / document.getSentences().size();
        double minPolAvg = minPol.stream().mapToDouble(d -> d).sum() / document.getSentences().size();

        if (maxPolAvg >= 0 && minPolAvg >= 0) {
            polarity = maxPolAvg;
        } else if (maxPolAvg < 0 && minPolAvg < 0) {
            polarity = minPolAvg;
        } else if (maxPolAvg > 0 && minPolAvg < 0) {
            polarity = maxPolAvg - Math.abs(minPolAvg);
        } else {
            polarity = minPolAvg - Math.abs(maxPolAvg);
        }


        return polarity;
    }


    private int computePrevWordsDependencyInversion(Word word, List<Dependency> dependencies) {
        for (Dependency dependency : dependencies) {
            Word other = null;
            if (dependency.getGovernor().equals(word)) {
                other = dependency.getDependent();

            } else if (dependency.getDependent().equals(word)) {
                other = dependency.getGovernor();
            }
            if (other != null && other.getHasNegation() && other.getIndex() < word.getIndex()) {
                return 1;
            }
        }

        return 0;
    }

    private double computeWordPolarity(Word word) {
        return sentiWordNet.extract(word.getLemma(), posConversionMap.get(word.getPos()));
    }

    //returns the value of the intensifier if the word has one, otherwise 0
    private int computeIntensifier(Word word, List<Dependency> dependencies) {
        for (Dependency dependency : dependencies) {
            if (dependency.getDependent().equals(word)) {
                Intensifier intensifier = getIntensifierFromWord(dependency.getGovernor());
                if (intensifier != null) {
                    return intensifier.getIncrease();
                }
            }

            if (dependency.getGovernor().equals(word)) {
                Intensifier intensifier = getIntensifierFromWord(dependency.getDependent());
                if (intensifier != null) {
                    return intensifier.getIncrease();
                }
            }
        }

        return 0;
    }

    private Intensifier getIntensifierFromWord(Word word) {
        for (Intensifier intensifier : intensifiers) {
            if (intensifier.getName().equals(word.getLemma())) {
                return intensifier;
            }
        }

        return null;
    }

    //returns 1 if word has negation, otherwise 0
    private int computeNegation(Word word, List<Dependency> dependencies) {
        for (Dependency dependency : dependencies) {
            if (dependency.getRelation().equals(NEGATION) && (dependency.getGovernor().equals(word) ||
                    dependency.getDependent().equals(word))) {
                return 1;
            }
        }

        return 0;
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
                dependency.getGovernor().setHasNegation(true);
            }
        }

        return negations;
    }
}
