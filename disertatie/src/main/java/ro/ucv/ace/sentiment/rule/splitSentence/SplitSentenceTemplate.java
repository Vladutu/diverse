package ro.ucv.ace.sentiment.rule.splitSentence;

import org.springframework.data.util.Pair;
import ro.ucv.ace.parser.Dependency;
import ro.ucv.ace.parser.Sentence;
import ro.ucv.ace.parser.Word;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class SplitSentenceTemplate implements SplitSentenceRule {

    private static final String NEGATION_RELATION = "neg";

    @Override
    public Double executeRule(Sentence sentence, Function<Sentence, Double> algorithmFunction) {
        Pair<Sentence, Sentence> sentencePair = splitSentence(sentence);

        return executeAlgorithmOnSentences(sentencePair.getFirst(), sentencePair.getSecond(), algorithmFunction);
    }

    private Pair<Sentence, Sentence> splitSentence(Sentence sentence) {
        int index = findSplitWordIndex(sentence);
        int startIndex = removeFirstWordOnFirstSentence() ? 1 : 0;

        List<Word> firstWords = sentence.getWords().subList(startIndex, index - 1);
        List<Word> secondWords = sentence.getWords().subList(index, sentence.getWords().size());

        return Pair.of(createSentence(sentence.getDependencies(), firstWords), createSentence(sentence.getDependencies(), secondWords));
    }

    private Sentence createSentence(List<Dependency> dependencies, List<Word> words) {
        List<Dependency> acceptedDependencies = dependencies.stream()
                .filter(dependency -> words.contains(dependency.getGovernor()) &&
                        words.contains(dependency.getDependent()))
                .collect(Collectors.toList());

        return new Sentence(words, acceptedDependencies, null);
    }

    protected boolean hasNegation(Sentence sentence) {
        return sentence.getDependencies().stream()
                .anyMatch(dep -> dep.getRelation().equalsIgnoreCase(NEGATION_RELATION));
    }

    protected abstract int findSplitWordIndex(Sentence sentence);

    protected abstract boolean removeFirstWordOnFirstSentence();

    protected abstract Double executeAlgorithmOnSentences(Sentence first, Sentence second, Function<Sentence, Double> algorithmFunction);
}
