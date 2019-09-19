package ro.ucv.ace.sentiment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.WebApplicationContext;
import ro.ucv.ace.parser.Dependency;
import ro.ucv.ace.parser.GrammarParser;
import ro.ucv.ace.parser.Sentence;
import ro.ucv.ace.parser.Word;
import ro.ucv.ace.senticnet.SenticNetService;
import ro.ucv.ace.sentiment.rule.ComplementClauseRule;
import ro.ucv.ace.sentiment.rule.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SentimentalPolarityAlgorithm {

    private final GrammarParser grammarParser;

    private final List<Rule> rules;

    private SenticNetService senticNetService;

    private Dependency lastProcessed = null;

    private ComplementClauseRule complementClauseRule = new ComplementClauseRule();

    @Autowired
    public SentimentalPolarityAlgorithm(GrammarParser grammarParser, List<Rule> rules, SenticNetService senticNetService) {
        this.grammarParser = grammarParser;
        this.rules = rules;
        this.senticNetService = senticNetService;
    }

    public Pair<Dependency, Sentence> execute(String text) {
        Sentence sentence = grammarParser.parse(text);
        sentence.getWords().forEach(word -> word.setPolarity(senticNetService.findWordPolarity(word)));

        if (!complementClauseRule.applies(sentence)) {
            return executeAlgorithm(sentence);
        }

        return complementClauseRule.executeRule(sentence, this::executeAlgorithm);
    }

    private Pair<Dependency, Sentence> executeAlgorithm(Sentence sentence) {
        List<Dependency> dependencies = sentence.getDependencies();

        for (Dependency dependency : dependencies) {
            executeRules(dependency, sentence);
        }

        return Pair.of(lastProcessed, sentence);
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
