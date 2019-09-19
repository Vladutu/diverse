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
    private static final List<String> SECONDARY_RELATION_WITH_PREPOSITION = Arrays.asList("prep", "pobj");

    public OpenClausalComplementRule(SenticNetService senticNetService, boolean addRules) {
        super(senticNetService, addRules);
    }

    @Override
    protected void executeRule(Dependency dependency, Sentence sentence) {
        Word governor = dependency.getGovernor();
        Word dependent = dependency.getDependent();
        Dependency secondaryDependency = tryFindSecondaryDependency(dependent, sentence.getDependencies());
        if (secondaryDependency == null) {
            double governorPolarity = governor.getPolarity();
            double dependentPolarity = dependent.getPolarity();
            if (governorPolarity < 0) {
                setPolarity(dependency, governorPolarity);
                return;
            }

            setPolarity(dependency, dependentPolarity);
            return;
        }
        Word secondaryDependent = secondaryDependency.getDependent();
        Double tripleConceptPolarity = senticNetService.findConceptPolarity(governor, dependent, secondaryDependent);
        double dependencyPolarity = computeDependencyPolarity(dependency, secondaryDependent);

        if (tripleConceptPolarity != null) {
            int reversePolarity = dependencyPolarity * tripleConceptPolarity < 0 ? -1 : 1;
            setPolarity(dependency, reversePolarity * tripleConceptPolarity);
            setPolarity(secondaryDependency, reversePolarity * tripleConceptPolarity);
            return;
        }

        setPolarity(dependency, dependencyPolarity);
        setPolarity(secondaryDependency, dependencyPolarity);
    }

    @Override
    public boolean applies(Dependency dependency) {
        return ACCEPTED_RELATIONS.contains(dependency.getRelation());
    }

    private double computeDependencyPolarity(Dependency dependency, Word secondaryDependent) {
        Word dependent = dependency.getDependent();
        Word governor = dependency.getGovernor();
        Double conceptPolarity = senticNetService.findConceptPolarity(dependent, secondaryDependent);
        double governorPolarity = governor.getPolarity();
        double dependentPolarity = dependent.getPolarity();
        double secondaryDependentPolarity = secondaryDependent.getPolarity();

        if (conceptPolarity != null && governorPolarity != 0) {
            if (governorPolarity < 0) {
                return -Math.abs(conceptPolarity);
            }

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
        Dependency dependency = dependencies.stream()
                .filter(dep -> dep.getGovernor().equals(dependent))
                .filter(dep -> SECONDARY_RELATIONS.contains(dep.getRelation()))
                .findAny()
                .orElse(null);
        if (dependency != null) {
            return dependency;
        }

        Dependency prepositionDependency = dependencies.stream()
                .filter(dep -> dep.getGovernor().equals(dependent))
                .filter(dep -> SECONDARY_RELATION_WITH_PREPOSITION.get(0).equals(dep.getRelation()))
                .findAny()
                .orElse(null);
        if (prepositionDependency == null) {
            return null;
        }

        return dependencies.stream()
                .filter(dep -> dep.getGovernor().equals(prepositionDependency.getDependent()))
                .filter(dep -> SECONDARY_RELATION_WITH_PREPOSITION.get(1).equals(dep.getRelation()))
                .findAny()
                .orElse(null);
    }
}
