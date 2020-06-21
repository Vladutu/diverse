package ro.ucv.ace.sentiment.rule;

import ro.ucv.ace.parser.Dependency;
import ro.ucv.ace.parser.Sentence;
import ro.ucv.ace.parser.Word;
import ro.ucv.ace.senticnet.PolarityService;

import java.util.ArrayList;
import java.util.List;

public abstract class RuleTemplate implements Rule {

    private final List<Rule> rulesActivatedFirst;
    protected PolarityService polarityService;

    public RuleTemplate(PolarityService polarityService, boolean addRules) {
        this.polarityService = polarityService;
        rulesActivatedFirst = new ArrayList<>();

        if (!addRules) {
            return;
        }
        rulesActivatedFirst.add(new SubjectNounRule(polarityService, false));
        rulesActivatedFirst.add(new AdjectivalAndAdverbialModifierRule(polarityService, false));
    }

    protected abstract void executeRule(Dependency dependency, Sentence sentence);

    public void execute(Dependency dependency, Sentence sentence) {
        tryExecuteRulesThatAreActivatedFirst(dependency, sentence);
        executeRule(dependency, sentence);
    }

    private void tryExecuteRulesThatAreActivatedFirst(Dependency thisDependency, Sentence sentence) {
        Word governor = thisDependency.getGovernor();
        Word dependent = thisDependency.getDependent();

        sentence.getDependencies().stream()
                .filter(dep -> !dep.equals(thisDependency))
                .filter(dep -> !dep.isProcessed())
                .filter(dep -> dep.getGovernor().equals(governor) ||
                        dep.getGovernor().equals(dependent) ||
                        dep.getDependent().equals(governor) ||
                        dep.getDependent().equals(dependent))
                .forEach(dep -> executeIfRuleMatches(dep, sentence));
    }

    private void executeIfRuleMatches(Dependency dependency, Sentence sentence) {
        rulesActivatedFirst.stream()
                .filter(rule -> rule.applies(dependency))
                .findFirst()
                .ifPresent(rule -> rule.execute(dependency, sentence));
    }
}
