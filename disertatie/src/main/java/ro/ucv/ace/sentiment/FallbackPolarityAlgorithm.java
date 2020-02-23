package ro.ucv.ace.sentiment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ro.ucv.ace.parser.Dependency;
import ro.ucv.ace.parser.Sentence;
import ro.ucv.ace.parser.Word;
import ro.ucv.ace.senticnet.WordPolarityService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class FallbackPolarityAlgorithm {

    private List<Intensifier> intensifiers;

    private final WordPolarityService wordPolarityService;

    private List<String> acceptedPos;

    @Autowired
    public FallbackPolarityAlgorithm(@Qualifier("senticWordNetService") WordPolarityService wordPolarityService) {
        this.wordPolarityService = wordPolarityService;
        loadIntensifiers();
        populateAcceptedPos();
    }

    private void populateAcceptedPos() {
        acceptedPos = Arrays.asList("NN", "NNS", "NNP", "NNPS", "JJ", "JJR", "JJS", "VB", "VBD", "VBG", "VBN",
                "VBP", "VBZ", "RB", "RBR", "RBS");
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

    public double computePolarity(Sentence sentence) {
        double polarity = 0;

        List<Word> negations = determineNegations(sentence.getWords());
        List<Word> intensifiers = determineIntensifiers(sentence.getWords());
        List<Word> excludes = Stream.of(negations, intensifiers).flatMap(List::stream).collect(Collectors.toList());

        double maxPolWord = -5;
        double minPolWord = 5;

        for (Word word : sentence.getWords()) {
            if (excludes.contains(word) || !acceptedPos.contains(word.getPos())) {
                continue;
            }

            int neg = word.isNegated() ? 1 : 0;
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

        if (maxPolWord >= 0 && minPolWord >= 0) {
            polarity = maxPolWord;
        } else if (maxPolWord < 0 && minPolWord < 0) {
            polarity = minPolWord;
        } else if (maxPolWord > 0 && minPolWord < 0) {
            polarity = maxPolWord - Math.abs(minPolWord);
        } else {
            polarity = minPolWord - Math.abs(maxPolWord);
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
            if (other != null && other.isNegated() && other.getIndex() < word.getIndex()) {
                return 1;
            }
        }

        return 0;
    }

    private double computeWordPolarity(Word word) {
        return wordPolarityService.findWordPolarity(word);
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

    private List<Word> determineNegations(List<Word> words) {
        return words.stream()
                .filter(Word::isNegated)
                .collect(Collectors.toList());
    }
}
