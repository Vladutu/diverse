package ro.ucv.ace.sentiment.rule.splitSentence;

import ro.ucv.ace.parser.Sentence;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public abstract class AdversariesSplitRule extends SplitSentenceTemplate {

    protected static final List<List<String>> ACCEPTED_WORDS = Arrays.asList(
            Arrays.asList("even", "though"),
            Arrays.asList("although"),
            Arrays.asList("despite"),
            Arrays.asList("in", "spite", "of")
    );

    @Override
    protected Double executeAlgorithmOnSentences(Sentence first, Sentence second, Function<Sentence, Double> algorithmFunction) {
        Double firstPolarity = algorithmFunction.apply(first);
        Double secondPolarity = algorithmFunction.apply(second);

        if (secondPolarity != null && secondPolarity != 0) {
            return secondPolarity;
        }

        if (firstPolarity == null) {
            return 0.0;
        }

        return -firstPolarity;
    }
}
