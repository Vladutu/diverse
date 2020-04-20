package ro.ucv.ace.sentiment.rule;

import ro.ucv.ace.parser.Dependency;
import ro.ucv.ace.parser.Sentence;
import ro.ucv.ace.parser.Word;
import ro.ucv.ace.senticnet.WordPolarityService;

import java.util.Arrays;
import java.util.List;

import static ro.ucv.ace.sentiment.SentimentUtils.neg;
import static ro.ucv.ace.sentiment.SentimentUtils.setPolarity;

public class AdjectivalAndAdverbialModifierRule extends RuleTemplate {

    private static final List<String> ACCEPTED_RELATIONS = Arrays.asList("amod", "advmod");

    public AdjectivalAndAdverbialModifierRule(WordPolarityService wordPolarityService, boolean addRules) {
        super(wordPolarityService, addRules);
    }

    @Override
    public void executeRule(Dependency dependency, Sentence sentence) {
        Word head = dependency.getGovernor();
        Word dependent = dependency.getDependent();

        double dependencyPolarity = computeDependencyPolarity(dependency);
        Double conceptPolarity = wordPolarityService.findConceptPolarity(head, dependent);
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

        if (dependantPolarity != 0) {
            return dependantPolarity;
        } else {
            return headPolarity;
        }
    }
}
