package ro.ucv.ace.sentiment.rule.splitSentence;

import ro.ucv.ace.parser.Sentence;

import java.util.function.Function;

public class DefaultSplitRule implements SentenceSplitRule {

    @Override
    public Double executeRule(Sentence sentence, Function<Sentence, Double> algorithmFunction) {
        return algorithmFunction.apply(sentence);
    }

    @Override
    public boolean applies(Sentence sentence) {
        return true;
    }
}
