package ro.ucv.ace.sentiment.rule;

import ro.ucv.ace.parser.Dependency;
import ro.ucv.ace.parser.Sentence;
import ro.ucv.ace.senticnet.PolarityService;

import static ro.ucv.ace.sentiment.SentimentUtils.setPolarity;

public class AgainstRule extends RuleTemplate {

    private static final String ACCEPTED_WORD = "against";
    private static final String ACCEPTED_RELATION = "pobj";

    public AgainstRule(PolarityService polarityService, boolean addRules) {
        super(polarityService, addRules);
    }

    @Override
    protected void executeRule(Dependency dependency, Sentence sentence) {
        if (dependency.getGovernor().getValue().equalsIgnoreCase(ACCEPTED_WORD)) {
            if (dependency.getDependent().getPolarity() != 0) {
                setPolarity(dependency, -dependency.getDependent().getPolarity());
                return;
            } else {
                setPolarity(dependency, dependency.getGovernor().getPolarity());
                return;
            }
        }

        if (dependency.getGovernor().getPolarity() != 0) {
            setPolarity(dependency, -dependency.getGovernor().getPolarity());
        } else {
            setPolarity(dependency, dependency.getDependent().getPolarity());
        }
    }

    @Override
    public boolean applies(Dependency dependency) {
        return ACCEPTED_RELATION.equalsIgnoreCase(dependency.getRelation()) && (
                ACCEPTED_WORD.equalsIgnoreCase(dependency.getGovernor().getValue()) ||
                        ACCEPTED_WORD.equalsIgnoreCase(dependency.getDependent().getValue()));
    }
}
