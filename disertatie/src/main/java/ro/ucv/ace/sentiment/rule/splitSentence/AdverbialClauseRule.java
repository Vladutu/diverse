package ro.ucv.ace.sentiment.rule.splitSentence;

import org.springframework.data.util.Pair;
import ro.ucv.ace.parser.Sentence;
import ro.ucv.ace.parser.Word;

import java.util.List;
import java.util.function.Function;

public class AdverbialClauseRule implements SplitSentenceRule {

    private static final String ACCEPTED_WORD = "while";

    @Override
    public Double executeRule(Sentence sentence, Function<Sentence, Double> algorithmFunction) {
        Pair<Sentence, Sentence> sentencePair = splitSentence(sentence);

        Double firstPolarity = algorithmFunction.apply(sentencePair.getFirst());
        Double secondPolarity = algorithmFunction.apply(sentencePair.getSecond());

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

    private Pair<Sentence, Sentence> splitSentence(Sentence sentence) {
        int index = sentence.getParseNode().indexOfCommaAfterSubordinateClause();
        List<Word> firstWords = sentence.getWords().subList(1, index - 1);
        List<Word> secondWords = sentence.getWords().subList(index, sentence.getWords().size());

        return Pair.of(createSentence(sentence.getDependencies(), firstWords), createSentence(sentence.getDependencies(), secondWords));
    }
}
