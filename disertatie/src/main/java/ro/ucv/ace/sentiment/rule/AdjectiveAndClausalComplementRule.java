package ro.ucv.ace.sentiment.rule;

import ro.ucv.ace.parser.Dependency;
import ro.ucv.ace.parser.Sentence;
import ro.ucv.ace.parser.Word;
import ro.ucv.ace.senticnet.SenticNetService;

import java.util.Arrays;
import java.util.List;

import static ro.ucv.ace.sentiment.SentimentUtils.setPolarity;

public class AdjectiveAndClausalComplementRule extends RuleTemplate {

    private static final List<String> ACCEPTED_RELATIONS = Arrays.asList("acomp", "ccomp");

    public AdjectiveAndClausalComplementRule(SenticNetService senticNetService, boolean addRules) {
        super(senticNetService, addRules);
    }

    @Override
    protected void executeRule(Dependency dependency, Sentence sentence) {
        Word head = dependency.getGovernor();
        Word dependent = dependency.getDependent();

        double dependencyPolarity = computeDependencyPolarity(dependency);
        Double conceptPolarity = senticNetService.findConceptPolarity(head, dependent);

        if (conceptPolarity != null) {
            int reversePolarity = dependencyPolarity * conceptPolarity < 0 ? -1 : 1;
            setPolarity(dependency, reversePolarity * conceptPolarity);
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

        double headPolarity = computeWordPolarity(head);
        double dependantPolarity = computeWordPolarity(dependent);

        if (headPolarity != 0 && dependantPolarity != 0) {
            return dependantPolarity;
        }

        return dependantPolarity;
    }

    private double computeWordPolarity(Word word) {
        return word.getPolarity() != 0 ? word.getPolarity() : senticNetService.findWordPolarity(word);
    }
}
