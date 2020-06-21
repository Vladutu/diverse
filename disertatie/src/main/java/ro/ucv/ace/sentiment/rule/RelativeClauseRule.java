package ro.ucv.ace.sentiment.rule;

import ro.ucv.ace.parser.Dependency;
import ro.ucv.ace.parser.Sentence;
import ro.ucv.ace.parser.Word;
import ro.ucv.ace.senticnet.PolarityService;

import java.util.Arrays;
import java.util.List;

import static ro.ucv.ace.sentiment.SentimentUtils.neg;
import static ro.ucv.ace.sentiment.SentimentUtils.setPolarity;

public class RelativeClauseRule extends RuleTemplate {

    private static final List<String> ACCEPTED_RELATIONS = Arrays.asList("rcmod");

    public RelativeClauseRule(PolarityService polarityService, boolean addRules) {
        super(polarityService, addRules);
    }

    @Override
    protected void executeRule(Dependency dependency, Sentence sentence) {
        Word head = dependency.getGovernor();
        Word dependent = dependency.getDependent();

        double dependencyPolarity = computeDependencyPolarity(dependency);
        Double conceptPolarity = polarityService.findConceptPolarity(head, dependent);
        if (conceptPolarity != null) {
            int polarityFactor = neg(dependencyPolarity * conceptPolarity) ? -1 : 1;
            setPolarity(dependency, polarityFactor * conceptPolarity);
            return;
        }

        setPolarity(dependency, dependencyPolarity);
    }

    @Override
    public boolean applies(Dependency dependency) {
        return ACCEPTED_RELATIONS.contains(dependency.getRelation());
    }

    private double computeDependencyPolarity(Dependency dependency) {
        Word dependent = dependency.getDependent();
        Word governor = dependency.getGovernor();
        double dependentPolarity = dependent.getPolarity();
        double governorPolarity = governor.getPolarity();

        if (dependentPolarity != 0) {
            return dependentPolarity;
        }

        return governorPolarity;
    }
}
