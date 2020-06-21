package ro.ucv.ace.sentiment.rule.splitSentence;

import ro.ucv.ace.parser.Sentence;

import java.util.function.Function;

public interface SentenceSplitRule {
    Double executeRule(Sentence sentence, Function<Sentence, Double> algorithmFunction);

    boolean applies(Sentence sentence);
}
