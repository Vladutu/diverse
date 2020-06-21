package ro.ucv.ace.sentiment.rule.splitSentence;

import org.springframework.data.util.Pair;
import ro.ucv.ace.parser.Sentence;

import java.util.function.Function;

public class AdverbialClauseSplitRule extends SentenceSplitTemplate {

    private static final String ACCEPTED_WORD = "while";

    @Override
    protected Pair<Integer, Integer> findSplitWordIndexRange(Sentence sentence) {
        return Pair.of(sentence.getParseNode().indexOfCommaAfterSubordinateClause(), sentence.getParseNode().indexOfCommaAfterSubordinateClause());
    }

    @Override
    protected int numberOfWordsToRemoveAtTheBeginningOfTheSentence(Sentence sentence) {
        return 1;
    }

    @Override
    protected Double executeAlgorithmOnSentences(Sentence first, Sentence second, Function<Sentence, Double> algorithmFunction) {
        Double firstPolarity = algorithmFunction.apply(first);
        Double secondPolarity = algorithmFunction.apply(second);

        if (secondPolarity != 0) {
            return secondPolarity;
        }

        return -firstPolarity;
    }

    @Override
    public boolean applies(Sentence sentence) {
        return sentence.getWords().get(0).getValue().equalsIgnoreCase(ACCEPTED_WORD) &&
                sentence.getParseNode().indexOfCommaAfterSubordinateClause() != -1;
    }
}
