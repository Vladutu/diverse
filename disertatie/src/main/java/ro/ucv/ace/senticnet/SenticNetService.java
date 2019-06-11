package ro.ucv.ace.senticnet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ucv.ace.parser.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ro.ucv.ace.sentiment.rule.SentimentUtils.neg;
import static ro.ucv.ace.sentiment.rule.SentimentUtils.pos;

@Service
public class SenticNetService {

    private SenticNetRepository senticNetRepository;

    @Autowired
    public SenticNetService(SenticNetRepository senticNetRepository) {
        this.senticNetRepository = senticNetRepository;
    }

    public Double findConceptPolarity(Word w1, Word w2) {
        List<String> concepts = new ArrayList<>();
        concepts.add(createConcept(w1.getValue(), w2.getValue()));
        concepts.add(createConcept(w1.getValue(), w2.getLemma()));
        concepts.add(createConcept(w1.getLemma(), w2.getValue()));
        concepts.add(createConcept(w1.getLemma(), w2.getLemma()));

        return getSenticFromConcepts(concepts);
    }

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

    public double findWordPolarity(Word word) {
        String newWord = word.getValue().replaceAll(" ", "_");
        String newLemma = word.getLemma().replaceAll(" ", "_");
        Double wordSenticPolarity = senticNetRepository.findById(newWord).map(SenticNet::getPolarityValue).orElse(null);
        Double lemmaSenticPolarity = senticNetRepository.findById(newLemma).map(SenticNet::getPolarityValue).orElse(null);

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

    private Double getSenticFromConcepts(List<String> concepts) {
        return concepts.stream()
                .map(senticNetRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(SenticNet::getPolarityValue)
                .findFirst()
                .orElse(null);
    }

    private String createConcept(String... words) {
        return Stream.of(words)
                .map(word -> word.replaceAll(" ", "_"))
                .collect(Collectors.joining("_"));
    }

}
