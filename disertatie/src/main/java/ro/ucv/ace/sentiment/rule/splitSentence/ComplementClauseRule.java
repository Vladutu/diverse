package ro.ucv.ace.sentiment.rule.splitSentence;

import ro.ucv.ace.parser.Sentence;
import ro.ucv.ace.parser.Word;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class ComplementClauseRule extends SplitSentenceTemplate {

    private static final List<String> ACCEPTED_WORDS = Arrays.asList("that", "whether");

    @Override
    protected int findSplitWordIndex(Sentence sentence) {
        return findAcceptedWord(sentence).getIndex();
    }

    @Override
    protected boolean removeFirstWordOnFirstSentence() {
        return false;
    }

    @Override
    protected Double executeAlgorithmOnSentences(Sentence first, Sentence second, Function<Sentence, Double> algorithmFunction) {
        Double firstPolarity = algorithmFunction.apply(first);
        Double secondPolarity = algorithmFunction.apply(second);

        if (firstPolarity != null && firstPolarity != 0) {
            return firstPolarity;
        } else {
            if (hasNegation(first)) {
                return -secondPolarity;
            }

            return secondPolarity;
        }
    }

    @Override
    public boolean applies(Sentence sentence) {
        return findAcceptedWord(sentence) != null;
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
