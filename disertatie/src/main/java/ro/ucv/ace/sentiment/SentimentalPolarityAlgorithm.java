package ro.ucv.ace.sentiment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.WebApplicationContext;
import ro.ucv.ace.parser.Dependency;
import ro.ucv.ace.parser.GrammarParser;
import ro.ucv.ace.parser.Sentence;
import ro.ucv.ace.parser.Word;
import ro.ucv.ace.senticnet.SenticNetService;
import ro.ucv.ace.sentiment.rule.FirstPersonRule;
import ro.ucv.ace.sentiment.rule.Rule;
import ro.ucv.ace.sentiment.rule.splitSentence.DefaultSplitSentenceRule;
import ro.ucv.ace.sentiment.rule.splitSentence.SplitSentenceRule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SentimentalPolarityAlgorithm {

    private final GrammarParser grammarParser;

    private final List<Rule> rules;

    private SenticNetService senticNetService;

    private List<SplitSentenceRule> splitSentenceRules;

    private Dependency lastProcessed = null;

    private FirstPersonRule firstPersonRule = new FirstPersonRule();

    @Autowired
    public SentimentalPolarityAlgorithm(GrammarParser grammarParser, List<Rule> rules, SenticNetService senticNetService,
                                        List<SplitSentenceRule> splitSentenceRules) {
        this.grammarParser = grammarParser;
        this.rules = rules;
        this.senticNetService = senticNetService;
        this.splitSentenceRules = splitSentenceRules;
    }

    public Double execute(String text) {
        Sentence sentence = grammarParser.parse(text);
        setWordPolarities(sentence);

        return splitSentenceRules.stream()
                .filter(rule -> rule.applies(sentence))
                .findFirst()
                .orElseGet(DefaultSplitSentenceRule::new)
                .executeRule(sentence, this::executeAlgorithm);
    }

    private void setWordPolarities(Sentence sentence) {
        sentence.getWords().forEach(word -> {
            int polarityFactor = word.isNegated() ? -1 : 1;
            word.setPolarity(polarityFactor * senticNetService.findWordPolarity(word));
        });
    }

    private Double executeAlgorithm(Sentence sentence) {
        double polarity = firstPersonRule.execute(sentence);
        if (polarity != 0) {
            return polarity;
        }

        sentence.getDependencies().forEach(dep -> executeRules(dep, sentence));

        if (lastProcessed == null) {
            return 0.0;
        }
        return lastProcessed.getPolarity();
    }

    private void executeRules(Dependency dependency, Sentence sentence) {
        if (dependency.isProcessed() || !dependency.isProcessable()) {
            return;
        }

        List<Rule> activableRules = rules.stream()
                .filter(rule -> rule.applies(dependency))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(activableRules)) {
            dependency.setProcessable(false);
            return;
        }

        activableRules
                .forEach(rule -> {
                    rule.execute(dependency, sentence);
                    lastProcessed = dependency;
                    List<Dependency> nextDependencies = findNextDependencies(dependency, sentence);

                    nextDependencies.forEach(nextDependency -> executeRules(nextDependency, sentence));
                });
    }

    private List<Dependency> findNextDependencies(Dependency dependency, Sentence sentence) {
        Word governor = dependency.getGovernor();
        Word dependent = dependency.getDependent();

        List<Dependency> nextDependencies = new ArrayList<>();
        sentence.getDependencies().stream()
                .filter(dep -> dep.getGovernor().equals(governor))
                .filter(dep -> !dep.equals(dependency))
                .forEach(nextDependencies::add);
        sentence.getDependencies().stream()
                .filter(dep -> dep.getGovernor().equals(dependent))
                .filter(dep -> !dep.equals(dependency))
                .forEach(nextDependencies::add);

        return nextDependencies;
    }
}
