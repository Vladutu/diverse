package ro.ucv.ace.sentiment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ro.ucv.ace.parser.GrammarParser;
import ro.ucv.ace.parser.Sentence;
import ro.ucv.ace.senticnet.WordPolarityService;
import ro.ucv.ace.sentiment.rule.Rule;
import ro.ucv.ace.sentiment.rule.splitSentence.DefaultSplitSentenceRule;
import ro.ucv.ace.sentiment.rule.splitSentence.SplitSentenceRule;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SentimentalPolarityAlgorithm {

    private final GrammarParser grammarParser;

    private final List<Rule> rules;

    private final FallbackPolarityAlgorithm fallbackPolarityAlgorithm;

    private WordPolarityService wordPolarityService;

    private List<SplitSentenceRule> splitSentenceRules;

    @Autowired
    public SentimentalPolarityAlgorithm(GrammarParser grammarParser, List<Rule> rules, FallbackPolarityAlgorithm fallbackPolarityAlgorithm,
                                        @Qualifier("wordPolarityCombinedSenticWordNetPreferredService") WordPolarityService wordPolarityService,
                                        List<SplitSentenceRule> splitSentenceRules) {
        this.grammarParser = grammarParser;
        this.rules = rules;
        this.fallbackPolarityAlgorithm = fallbackPolarityAlgorithm;
        this.wordPolarityService = wordPolarityService;
        this.splitSentenceRules = splitSentenceRules;
    }

    public List<Double> execute(String text) {
        List<Sentence> sentences = grammarParser.parse(text);
        AlgorithmExecutor algorithmExecutor = new AlgorithmExecutor(rules, fallbackPolarityAlgorithm);

        return sentences.stream()
                .map(sentence -> calculatePolarity(sentence, algorithmExecutor))
                .collect(Collectors.toList());
    }

    private Double calculatePolarity(Sentence sentence, AlgorithmExecutor algorithmExecutor) {
        setWordPolarities(sentence);
        return splitSentenceRules.stream()
                .filter(rule -> rule.applies(sentence))
                .findFirst()
                .orElseGet(DefaultSplitSentenceRule::new)
                .executeRule(sentence, algorithmExecutor::executeAlgorithm);
    }

    private void setWordPolarities(Sentence sentence) {
        sentence.getWords().forEach(word -> {
            int polarityFactor = word.isNegated() ? -1 : 1;
            word.setPolarity(polarityFactor * wordPolarityService.findWordPolarity(word));
        });
    }
}
