package ro.ucv.ace.readability;

import org.springframework.stereotype.Component;
import ro.ucv.ace.utils.BasicTextProcessor;
import ro.ucv.ace.utils.TextProcessor;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Geo on 19.03.2017.
 */
@Component
public class ReadabilityImpl implements Readability {

    @Override
    public ReadabilityResult computeReadability(String text) {
        TextProcessor textProcessor = new BasicTextProcessor(text);
        int noSentences = textProcessor.numberOfSentences();
        int noComplexWords = textProcessor.numberOfComplexWords();
        int noWords = textProcessor.numberOfWords();
        int noSyllables = textProcessor.numberOfSyllables();
        int noCharacters = textProcessor.numberOfCharacters();

        double gunningFogIndex = computeGunningFogIndex(noWords, noSentences, noComplexWords);
        double fleschKincaidGradeLevel = computeFleschKincaidGradeLevel(noWords, noSentences, noSyllables);
        double fleschReadingEase = computeFleschReadingEase(noWords, noSentences, noSyllables);
        double automatedReadabilityIndex = computeAutomatedReadabilityIndex(noWords, noCharacters, noSentences);
        double colemanLiauIndex = computeColemanLiauIndex(noCharacters, noWords, noSentences);

        return new ReadabilityResult(gunningFogIndex, fleschKincaidGradeLevel, fleschReadingEase, automatedReadabilityIndex, colemanLiauIndex);
    }

    private double computeFleschReadingEase(int noWords, int noSentences, int noSyllables) {
        double score = 206.835 - (1.015 * (double) noWords / noSentences) - (84.6 * (double) noSyllables / noWords);
        return round(score, 3);
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

    private double round(double number, int precision) {
        return BigDecimal.valueOf(number).setScale(precision, RoundingMode.HALF_UP).doubleValue();
    }
}
