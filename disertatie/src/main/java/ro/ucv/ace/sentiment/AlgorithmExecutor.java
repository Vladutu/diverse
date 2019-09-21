package ro.ucv.ace.sentiment;

import org.springframework.util.CollectionUtils;
import ro.ucv.ace.parser.Dependency;
import ro.ucv.ace.parser.Sentence;
import ro.ucv.ace.parser.Word;
import ro.ucv.ace.sentiment.rule.FirstPersonRule;
import ro.ucv.ace.sentiment.rule.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AlgorithmExecutor {

    private Dependency lastProcessed = null;

    private FirstPersonRule firstPersonRule = new FirstPersonRule();

    private final List<Rule> rules;

    public AlgorithmExecutor(List<Rule> rules) {
        this.rules = rules;
    }

    public Double executeAlgorithm(Sentence sentence) {
        lastProcessed = null;
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
