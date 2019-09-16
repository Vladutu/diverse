package ro.ucv.ace.sentiment.rule;

import ro.ucv.ace.parser.Dependency;
import ro.ucv.ace.parser.Sentence;
import ro.ucv.ace.senticnet.SenticNetService;

import java.util.Arrays;
import java.util.List;

public class AdjectivalAndAdverbialModifierRule extends RuleTemplate {

    private static final List<String> ACCEPTED_RELATIONS = Arrays.asList("amod", "advmod");


    public AdjectivalAndAdverbialModifierRule(SenticNetService senticNetService, boolean addRules) {
        super(senticNetService, addRules);
    }

    @Override
    public void executeRule(Dependency dependency, Sentence sentence) {

    }

    @Override
    public boolean applies(Dependency dependency) {
        return ACCEPTED_RELATIONS.contains(dependency.getRelation());
    }
}
