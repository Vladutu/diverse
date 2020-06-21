package ro.ucv.ace.sentiment;

import ro.ucv.ace.parser.GrammarParser;
import ro.ucv.ace.parser.Sentence;
import ro.ucv.ace.senticnet.PolarityService;
import ro.ucv.ace.sentiment.rule.Rule;
import ro.ucv.ace.sentiment.rule.splitSentence.DefaultSplitRule;
import ro.ucv.ace.sentiment.rule.splitSentence.SentenceSplitRule;

import java.util.List;
import java.util.stream.Collectors;

public class SentimentalPolarityAlgorithm {

    private final GrammarParser grammarParser;

    private final List<Rule> rules;

    private final FallbackPolarityAlgorithm fallbackPolarityAlgorithm;

    private PolarityService polarityService;

    private List<SentenceSplitRule> sentenceSplitRules;

    public SentimentalPolarityAlgorithm(GrammarParser grammarParser, List<Rule> rules, FallbackPolarityAlgorithm fallbackPolarityAlgorithm,
                                        PolarityService polarityService,
                                        List<SentenceSplitRule> sentenceSplitRules) {
        this.grammarParser = grammarParser;
        this.rules = rules;
        this.fallbackPolarityAlgorithm = fallbackPolarityAlgorithm;
        this.polarityService = polarityService;
        this.sentenceSplitRules = sentenceSplitRules;
    }

    public List<Double> execute(String text) {
        List<Sentence> sentences = grammarParser.parse(text);
        return sentences.stream()
                .map(sentence -> calculatePolarity(sentence, new AlgorithmExecutor(rules, fallbackPolarityAlgorithm)))
                .collect(Collectors.toList());
    }

    private Double calculatePolarity(Sentence sentence, AlgorithmExecutor algorithmExecutor) {
        setWordPolarities(sentence);
        return sentenceSplitRules.stream()
                .filter(rule -> rule.applies(sentence))
                .findFirst()
                .orElseGet(DefaultSplitRule::new)
                .executeRule(sentence, algorithmExecutor::executeAlgorithm);
    }

    private void setWordPolarities(Sentence sentence) {
        sentence.getWords().forEach(word -> {
            int polarityFactor = word.isNegated() ? -1 : 1;
            word.setPolarity(polarityFactor * polarityService.findWordPolarity(word));
        });
    }
}
