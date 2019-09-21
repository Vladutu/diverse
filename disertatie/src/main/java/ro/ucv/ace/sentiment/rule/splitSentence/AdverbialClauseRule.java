package ro.ucv.ace.sentiment.rule.splitSentence;

import ro.ucv.ace.parser.Sentence;

import java.util.function.Function;

public class AdverbialClauseRule extends SplitSentenceTemplate {

    private static final String ACCEPTED_WORD = "while";

    @Override
    protected int findSplitWordIndex(Sentence sentence) {
        return sentence.getParseNode().indexOfCommaAfterSubordinateClause();
    }

    @Override
    protected boolean removeFirstWordOnFirstSentence() {
        return true;
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
