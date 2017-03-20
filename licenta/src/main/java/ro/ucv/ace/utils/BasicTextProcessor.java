package ro.ucv.ace.utils;

import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Geo on 20.03.2017.
 */
public class BasicTextProcessor implements TextProcessor {

    private String text;

    private Syllabifier syllabifier = new Syllabifier();

    private List<String> sentences;

    private List<String> words;

    private Integer noSyllables = null;

    private Integer noCharacters = null;

    private Integer noComplexWords = null;

    public BasicTextProcessor(String text) {
        this.text = text;
        Document document = new Document(text);
        this.sentences = document.sentences().parallelStream().map(Sentence::text).collect(Collectors.toList());
        this.words = sentences.parallelStream()
                .filter(sentence -> !cleanText(sentence).trim().isEmpty())
                .flatMap(sentence -> new Sentence(cleanText(sentence)).words().stream())
                .collect(Collectors.toList());
    }

    private String cleanText(String text) {
        return text.replaceAll("[^a-zA-Z0-9']", " ").replaceAll("'", "");
    }

    @Override
    public int numberOfSentences() {
        return sentences.size();
    }

    @Override
    public int numberOfWords() {
        return words.size() > 0 ? words.size() : 1;
    }

    @Override
    public int numberOfComplexWords() {
        if (noComplexWords == null) {
            noComplexWords = Math.toIntExact(words.parallelStream()
                    .filter(this::isComplex)
                    .count());
        }

        return noComplexWords;
    }

    private boolean isComplex(String word) {
        return syllabifier.countSyllables(word) > 2;
    }

    @Override
    public int numberOfSyllables() {
        if (noSyllables == null) {
            noSyllables = Math.toIntExact(words.parallelStream()
                    .mapToInt(word -> syllabifier.countSyllables(word))
                    .sum());
        }

        return noSyllables;
    }

    @Override
    public int numberOfCharacters() {
        if (noCharacters == null) {
            int characterCounter = 0;

            for (int i = 0; i < text.length(); i++) {
                if (Character.isLetterOrDigit(text.charAt(i))) {
                    characterCounter++;
                }
            }

            noCharacters = characterCounter;
        }

        return noCharacters;
    }
}
