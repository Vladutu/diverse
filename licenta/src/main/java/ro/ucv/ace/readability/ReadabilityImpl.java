package ro.ucv.ace.readability;

import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Geo on 19.03.2017.
 */
@Component
public class ReadabilityImpl implements Readability {

    @Autowired
    private Syllabifier syllabifier;

    @Override
    public ReadabilityResult computeReadability(String text) {
        Document document = new Document(text);
        List<Sentence> sentences = getSentences(document);
        List<String> words = getWords(sentences);

        int noSentences = getNoSentences(sentences);
        int noComplexWords = getNoComplexWords(words);
        int noWords = getNoWords(words);
        int noSyllables = getNoSyllables(words);
        int noCharacters = getNoCharacters(text);

        double gunningFogIndex = computeGunningFogIndex(noWords, noSentences, noComplexWords);
        double fleschKincaidGradeLevel = computeFleschKincaidGradeLevel(noWords, noSentences, noSyllables);
        double fleschReadingEase = computeFleschReadingEase(noWords, noSentences, noSyllables);
        double automatedReadabilityIndex = computeAutomatedReadabilityIndex(noWords, noCharacters, noSentences);
        double colemanLiauIndex = computeColemanLiauIndex(noCharacters, noWords, noSentences);

        return new ReadabilityResult(gunningFogIndex, fleschKincaidGradeLevel,fleschReadingEase, automatedReadabilityIndex, colemanLiauIndex);
    }

    private double computeFleschReadingEase(int noWords, int noSentences, int noSyllables) {
        double score = 206.835 - (1.015 * (double) noWords / noSentences) - (84.6 * (double) noSyllables / noWords);
        return round(score, 3);
    }

    private List<Sentence> getSentences(Document document) {
        List<Sentence> sentences = document.sentences();
        List<Sentence> result = new ArrayList<>();
        sentences.forEach(sentence -> {
            result.add(new Sentence(cleanText(sentence.text())));
        });

        return result;
    }

    private String cleanText(String text) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                builder.append(c);
            } else {
                builder.append(" ");
            }
        }

        return builder.toString();
    }

    private List<String> getWords(List<Sentence> sentences) {
        return sentences.stream().flatMap(sentence -> sentence.words().stream()).collect(Collectors.toList());
    }

    private double computeColemanLiauIndex(int noCharacters, int noWords, int noSentences) {
        double score = (5.89 * (double) noCharacters / noWords) - (30 * (double) noSentences / noWords) - 15.8;
        return round(score, 3);
    }

    private double computeAutomatedReadabilityIndex(int noWords, int noCharacters, int noSentences) {
        double score = (4.71 * (double) noCharacters / noWords) + (0.5 * (double) noWords / noSentences) - 21.43;
        return round(score, 3);
    }

    private double computeFleschKincaidGradeLevel(int noWords, int noSentences, int noSyllables) {
        double score = 0.39 * ((double) noWords / noSentences) + (11.8 * (double) noSyllables / noWords) - 15.59;
        return round(score, 3);
    }

    private double computeGunningFogIndex(int noWords, int noSentences, int noComplexWords) {
        double score = 0.4 * (((double) noWords / noSentences) + 100 * ((double) noComplexWords / noWords));
        return round(score, 3);
    }

    private int getNoComplexWords(List<String> words) {
        return Math.toIntExact(words.stream().filter(this::isComplex).count());
    }

    private boolean isComplex(String word) {
        return syllabifier.countSyllables(word) > 2;
    }

    private int getNoSentences(List<Sentence> sentences) {
        return sentences.size();
    }

    private int getNoSyllables(List<String> words) {
        int syllablesCounter = 0;
        syllablesCounter += words.stream().mapToInt(word -> syllabifier.countSyllables(word)).sum();

        return syllablesCounter;
    }

    private int getNoCharacters(String text) {
        int characterCounter = 0;

        for (int i = 0; i < text.length(); i++) {
            if (Character.isLetterOrDigit(text.charAt(i))) {
                characterCounter++;
            }
        }

        return characterCounter;
    }

    private int getNoWords(List<String> words) {
        return words.size();
    }

    private double round(double number, int precision) {
        return BigDecimal.valueOf(number).setScale(3, RoundingMode.HALF_UP).doubleValue();
    }
}
