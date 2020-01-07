package ro.ucv.ace.sentiment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ucv.ace.parser.GrammarParser;
import ro.ucv.ace.parser.Sentence;
import ro.ucv.ace.senticnet.SenticNetService;
import ro.ucv.ace.sentiment.rule.Rule;
import ro.ucv.ace.sentiment.rule.splitSentence.DefaultSplitSentenceRule;
import ro.ucv.ace.sentiment.rule.splitSentence.SplitSentenceRule;

import java.util.List;

@Component
public class SentimentalPolarityAlgorithm {

    private final GrammarParser grammarParser;

    private final List<Rule> rules;

    private final FallbackPolarityAlgorithm fallbackPolarityAlgorithm;

    private SenticNetService senticNetService;

    private List<SplitSentenceRule> splitSentenceRules;

    @Autowired
    public SentimentalPolarityAlgorithm(GrammarParser grammarParser, List<Rule> rules, FallbackPolarityAlgorithm fallbackPolarityAlgorithm,
                                        SenticNetService senticNetService, List<SplitSentenceRule> splitSentenceRules) {
        this.grammarParser = grammarParser;
        this.rules = rules;
        this.fallbackPolarityAlgorithm = fallbackPolarityAlgorithm;
        this.senticNetService = senticNetService;
        this.splitSentenceRules = splitSentenceRules;
    }

    public Double execute(String text) {
        Sentence sentence = grammarParser.parse(text);
        setWordPolarities(sentence);
        AlgorithmExecutor algorithmExecutor = new AlgorithmExecutor(rules, fallbackPolarityAlgorithm);

        return splitSentenceRules.stream()
                .filter(rule -> rule.applies(sentence))
                .findFirst()
                .orElseGet(DefaultSplitSentenceRule::new)
                .executeRule(sentence, algorithmExecutor::executeAlgorithm);
    }

    private void setWordPolarities(Sentence sentence) {
        sentence.getWords().forEach(word -> {
            int polarityFactor = word.isNegated() ? -1 : 1;
            word.setPolarity(polarityFactor * senticNetService.findWordPolarity(word));
        });
    }
}
