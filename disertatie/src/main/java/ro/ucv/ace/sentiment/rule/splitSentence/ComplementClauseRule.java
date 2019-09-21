package ro.ucv.ace.sentiment.rule.splitSentence;

import org.springframework.data.util.Pair;
import ro.ucv.ace.parser.Sentence;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class ComplementClauseRule extends SplitSentenceTemplate {

    private static final List<String> ACCEPTED_WORDS = Arrays.asList("that", "whether");

    @Override
    protected Pair<Integer, Integer> findSplitWordIndexRange(Sentence sentence) {
        int index = findAcceptedWord(sentence, ACCEPTED_WORDS).getIndex();
        return Pair.of(index, index);
    }

    @Override
    protected int numberOfWordsToRemoveAtTheBeginningOfTheSentence(Sentence sentence) {
        return 0;
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
        return findAcceptedWord(sentence, ACCEPTED_WORDS) != null;
    }
}
