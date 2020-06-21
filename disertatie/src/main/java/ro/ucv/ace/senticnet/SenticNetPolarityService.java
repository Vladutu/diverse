package ro.ucv.ace.senticnet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ucv.ace.parser.Word;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ro.ucv.ace.sentiment.SentimentUtils.neg;
import static ro.ucv.ace.sentiment.SentimentUtils.pos;

@Service("senticNetPolarityService")
public class SenticNetPolarityService implements PolarityService {

    private SenticNetRepository senticNetRepository;

    @Autowired
    public SenticNetPolarityService(SenticNetRepository senticNetRepository) {
        this.senticNetRepository = senticNetRepository;
    }

    @Override
    public Double findConceptPolarity(Word w1, Word w2) {
        List<String> concepts = new ArrayList<>();
        concepts.add(createConcept(w1.getValue(), w2.getValue()));
        concepts.add(createConcept(w1.getValue(), w2.getLemma()));
        concepts.add(createConcept(w1.getLemma(), w2.getValue()));
        concepts.add(createConcept(w1.getLemma(), w2.getLemma()));

        return getSenticFromConcepts(concepts);
    }

    @Override
    public Double findConceptPolarity(Word w1, Word w2, Word w3) {
        List<String> concepts = new ArrayList<>();
        concepts.add(createConcept(w1.getValue(), w2.getValue(), w3.getValue()));
        concepts.add(createConcept(w1.getValue(), w2.getValue(), w3.getLemma()));
        concepts.add(createConcept(w1.getValue(), w2.getLemma(), w3.getValue()));
        concepts.add(createConcept(w1.getValue(), w2.getLemma(), w3.getLemma()));
        concepts.add(createConcept(w1.getLemma(), w2.getValue(), w3.getValue()));
        concepts.add(createConcept(w1.getLemma(), w2.getValue(), w3.getLemma()));
        concepts.add(createConcept(w1.getLemma(), w2.getLemma(), w3.getValue()));
        concepts.add(createConcept(w1.getLemma(), w2.getLemma(), w3.getLemma()));

        return getSenticFromConcepts(concepts);
    }

    @Override
    public double findWordPolarity(Word word) {
        String newWord = word.getValue().replaceAll(" ", "_");
        String newLemma = word.getLemma().replaceAll(" ", "_");
        Double wordSenticPolarity = findPolarity(newWord);
        Double lemmaSenticPolarity = findPolarity(newLemma);

        if (wordSenticPolarity == null && lemmaSenticPolarity == null) {
            return 0;
        }
        if (wordSenticPolarity == null) {
            return lemmaSenticPolarity;
        } else if (lemmaSenticPolarity == null) {
            return wordSenticPolarity;
        } else if (neg(wordSenticPolarity) && neg(lemmaSenticPolarity)) {
            return wordSenticPolarity < lemmaSenticPolarity ? wordSenticPolarity : lemmaSenticPolarity;
        } else if (pos(wordSenticPolarity) && pos(lemmaSenticPolarity)) {
            return wordSenticPolarity > lemmaSenticPolarity ? wordSenticPolarity : lemmaSenticPolarity;
        } else {
            return wordSenticPolarity;
        }
    }

    private Double findPolarity(String word) {
        return senticNetRepository
                .findById(word)
                .map(SenticNet::getPolarityValue)
                .orElse(null);
    }

    private Double getSenticFromConcepts(List<String> concepts) {
        return concepts.stream()
                .map(senticNetRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(SenticNet::getPolarityValue)
                .max(Comparator.comparingDouble(Math::abs))
                .orElse(null);
    }

    private String createConcept(String... words) {
        return Stream.of(words)
                .map(word -> word.replaceAll(" ", "_"))
                .collect(Collectors.joining("_"));
    }
}
