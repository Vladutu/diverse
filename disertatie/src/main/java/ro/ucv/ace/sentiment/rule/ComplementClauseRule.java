package ro.ucv.ace.sentiment.rule;

import org.springframework.data.util.Pair;
import ro.ucv.ace.parser.Dependency;
import ro.ucv.ace.parser.Sentence;
import ro.ucv.ace.parser.Word;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ComplementClauseRule {

    private static final List<String> ACCEPTED_WORDS = Arrays.asList("that", "whether");
    private static final String NEGATION_RELATION = "neg";

    public Double executeRule(Sentence sentence, Function<Sentence, Double> algorithmFunction) {
        Pair<Sentence, Sentence> sentencePair = splitSentence(sentence);

        Double firstPolarity = algorithmFunction.apply(sentencePair.getFirst());
        Double secondPolarity = algorithmFunction.apply(sentencePair.getSecond());

        if (firstPolarity != null && firstPolarity != 0) {
            return firstPolarity;
        } else {
            if (hasNegation(sentencePair.getFirst())) {
                return -secondPolarity;
            }

            return secondPolarity;
        }
    }

    private boolean hasNegation(Sentence sentence) {
        return sentence.getDependencies().stream()
                .anyMatch(dep -> dep.getRelation().equalsIgnoreCase(NEGATION_RELATION));
    }

    private Pair<Sentence, Sentence> splitSentence(Sentence sentence) {
        Word acceptedWord = findAcceptedWord(sentence);

        List<Word> firstWords = sentence.getWords().subList(0, acceptedWord.getIndex() - 1);
        List<Word> secondWords = sentence.getWords().subList(acceptedWord.getIndex(), sentence.getWords().size());

        return Pair.of(createSentence(sentence.getDependencies(), firstWords), createSentence(sentence.getDependencies(), secondWords));
    }

    public boolean applies(Sentence sentence) {
        return findAcceptedWord(sentence) != null;
    }

    private Sentence createSentence(List<Dependency> dependencies, List<Word> words) {
        List<Dependency> acceptedDependencies = dependencies.stream()
                .filter(dependency -> words.contains(dependency.getGovernor()) &&
                        words.contains(dependency.getDependent()))
                .collect(Collectors.toList());

        return new Sentence(words, acceptedDependencies);
    }

    private Word findAcceptedWord(Sentence sentence) {
        return sentence.getWords().stream()
                .filter(this::wordMatches)
                .findFirst()
                .orElse(null);
    }

    private boolean wordMatches(Word word) {
        return ACCEPTED_WORDS.stream()
                .anyMatch(acceptedWord -> acceptedWord.equalsIgnoreCase(word.getValue()));
    }
}
