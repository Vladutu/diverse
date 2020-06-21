package ro.ucv.ace.sentiment.rule;

import ro.ucv.ace.parser.Dependency;
import ro.ucv.ace.parser.Sentence;
import ro.ucv.ace.parser.Word;
import ro.ucv.ace.senticnet.PolarityService;

import java.util.Arrays;
import java.util.List;

import static ro.ucv.ace.sentiment.SentimentUtils.neg;
import static ro.ucv.ace.sentiment.SentimentUtils.setPolarity;

public class AdjectiveAndClausalComplementRule extends RuleTemplate {

    private static final List<String> ACCEPTED_RELATIONS = Arrays.asList("acomp", "ccomp");

    public AdjectiveAndClausalComplementRule(PolarityService polarityService, boolean addRules) {
        super(polarityService, addRules);
    }

    @Override
    protected void executeRule(Dependency dependency, Sentence sentence) {
        Word head = dependency.getGovernor();
        Word dependent = dependency.getDependent();

        double dependencyPolarity = computeDependencyPolarity(dependency);
        Double conceptPolarity = polarityService.findConceptPolarity(head, dependent);

        if (conceptPolarity != null) {
            int reversePolarityFactor = neg(dependencyPolarity * conceptPolarity) ? -1 : 1;
            setPolarity(dependency, reversePolarityFactor * conceptPolarity);
            return;
        }

        setPolarity(dependency, dependencyPolarity);
    }

    @Override
    public boolean applies(Dependency dependency) {
        return ACCEPTED_RELATIONS.contains(dependency.getRelation());
    }

    private double computeDependencyPolarity(Dependency dependency) {
        Word head = dependency.getGovernor();
        Word dependent = dependency.getDependent();

        double headPolarity = head.getPolarity();
        double dependantPolarity = dependent.getPolarity();

        if (headPolarity != 0 && dependantPolarity != 0) {
            return dependantPolarity;
        }

        if (dependantPolarity != 0) {
            return dependantPolarity;
        }

        return headPolarity;
    }
}
