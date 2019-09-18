package ro.ucv.ace.sentiment.rule;

import ro.ucv.ace.parser.Dependency;
import ro.ucv.ace.parser.Sentence;
import ro.ucv.ace.parser.Word;
import ro.ucv.ace.senticnet.SenticNetService;

import java.util.Arrays;
import java.util.List;

import static ro.ucv.ace.sentiment.SentimentUtils.*;

public class OpenClausalComplementRule extends RuleTemplate {

    private static final List<String> ACCEPTED_RELATIONS = Arrays.asList("xcomp");
    private static final List<String> SECONDARY_RELATIONS = Arrays.asList("ccomp", "dobj");

    public OpenClausalComplementRule(SenticNetService senticNetService, boolean addRules) {
        super(senticNetService, addRules);
    }

    @Override
    protected void executeRule(Dependency dependency, Sentence sentence) {
        Word governor = dependency.getGovernor();
        Word dependent = dependency.getDependent();
        Dependency secondaryDependency = tryFindSecondaryDependency(dependent, sentence.getDependencies());
        if (secondaryDependency == null) {
            setPolarity(dependency, 0);
            return;
        }
        Word secondaryDependent = secondaryDependency.getDependent();
        Double tripleConceptPolarity = senticNetService.findConceptPolarity(governor, dependent, secondaryDependent);
        double dependencyPolarity = computeDependencyPolarity(dependency, secondaryDependent, sentence);

        if (tripleConceptPolarity != null) {
            int reversePolarity = dependencyPolarity * tripleConceptPolarity < 0 ? -1 : 1;
            setPolarity(dependency, reversePolarity * tripleConceptPolarity);
            return;
        }

        setPolarity(dependency, dependencyPolarity);
    }

    @Override
    public boolean applies(Dependency dependency) {
        return ACCEPTED_RELATIONS.contains(dependency.getRelation());
    }

    private double computeDependencyPolarity(Dependency dependency, Word secondaryDependent, Sentence sentence) {
        Word dependent = dependency.getDependent();
        Word governor = dependency.getGovernor();
        Double conceptPolarity = senticNetService.findConceptPolarity(dependent, secondaryDependent);
        double governorPolarity = computeWordPolarity(governor);
        double dependentPolarity = computeWordPolarity(dependent);
        double secondaryDependentPolarity = computeWordPolarity(secondaryDependent);

        if (conceptPolarity != null && governorPolarity != 0) {
            return conceptPolarity;
        }

        if (dependentPolarity == 0) {
            if (pos(governorPolarity) && pos(secondaryDependentPolarity)) {
                return Math.max(governorPolarity, secondaryDependentPolarity);
            }
            if (pos(governorPolarity) && neg(secondaryDependentPolarity)) {
                return secondaryDependentPolarity;
            }
            if (neg(governorPolarity) && pos(secondaryDependentPolarity)) {
                return governorPolarity;
            }
            if (neg(governorPolarity) && neg(secondaryDependentPolarity)) {
                return Math.min(governorPolarity, secondaryDependentPolarity);
            }
        }

        if (pos(governorPolarity) && pos(dependentPolarity) && pos(secondaryDependentPolarity)) {
            return Math.max(secondaryDependentPolarity, Math.max(governorPolarity, dependentPolarity));
        }
        if (pos(governorPolarity) && pos(dependentPolarity) && neg(secondaryDependentPolarity)) {
            return secondaryDependentPolarity;
        }
        if (pos(governorPolarity) && neg(dependentPolarity) && pos(secondaryDependentPolarity)) {
            return dependentPolarity;
        }
        if (pos(governorPolarity) && neg(dependentPolarity) && neg(secondaryDependentPolarity)) {
            return governorPolarity;
        }
        if (neg(governorPolarity) && pos(dependentPolarity) && pos(secondaryDependentPolarity)) {
            return governorPolarity;
        }
        if (neg(governorPolarity) && pos(dependentPolarity) && neg(secondaryDependentPolarity)) {
            return Math.min(governorPolarity, secondaryDependentPolarity);
        }
        if (neg(governorPolarity) && neg(dependentPolarity) && pos(secondaryDependentPolarity)) {
            return Math.min(governorPolarity, dependentPolarity);
        }
        if (neg(governorPolarity) && neg(dependentPolarity) && neg(secondaryDependentPolarity)) {
            return Math.min(secondaryDependentPolarity, Math.min(governorPolarity, dependentPolarity));
        }

        return 0;
    }

    private Dependency tryFindSecondaryDependency(Word dependent, List<Dependency> dependencies) {
        return dependencies.stream()
                .filter(dep -> dep.getGovernor().equals(dependent))
                .filter(dep -> SECONDARY_RELATIONS.contains(dep.getRelation()))
                .findAny()
                .orElse(null);
    }

    private double computeWordPolarity(Word word) {
        return word.getPolarity() != 0 ? word.getPolarity() : senticNetService.findWordPolarity(word);
    }
}
