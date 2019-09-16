package ro.ucv.ace.sentiment.rule;

import ro.ucv.ace.parser.Dependency;
import ro.ucv.ace.parser.Sentence;
import ro.ucv.ace.parser.Word;
import ro.ucv.ace.senticnet.SenticNetService;

import java.util.Arrays;
import java.util.List;

import static ro.ucv.ace.sentiment.rule.SentimentUtils.*;

public class SubjectNounRule extends RuleTemplate {

    private static final List<String> ACCEPTED_RELATIONS = Arrays.asList("nsubj", "nsubjpass");
    private static final String PASSIVE_VOICE_TOKEN = ACCEPTED_RELATIONS.get(1);

    public SubjectNounRule(SenticNetService senticNetService, boolean addRules) {
        super(senticNetService, addRules);
    }

    @Override
    public void executeRule(Dependency dependency, Sentence sentence) {
        Word head = dependency.getGovernor();
        Word dependent = dependency.getDependent();

        Double conceptPolarity = senticNetService.findConceptPolarity(head, dependent);
        if (conceptPolarity != null) {
            setPolarity(dependency, conceptPolarity);
            return;
        }

        double headPolarity = computeWordPolarity(head);
        double dependantPolarity = computeWordPolarity(dependent);
        boolean passiveVoice = dependency.getRelation().equals(PASSIVE_VOICE_TOKEN);

        if (neg(headPolarity) && neg(dependantPolarity)) {
            if (passiveVoice) {
                setPolarity(dependency, -Math.min(headPolarity, dependantPolarity));
            } else {
                setPolarity(dependency, Math.min(headPolarity, dependantPolarity));
            }
        } else if (neg(headPolarity) && pos(dependantPolarity)) {
            // We do not use the first person part because it's not correct
            setPolarity(dependency, Math.min(headPolarity, -dependantPolarity));
        } else if (pos(headPolarity) && neg(dependantPolarity)) {
            // We changed to positive from the examples
            setPolarity(dependency, Math.max(headPolarity, -dependantPolarity));
        } else if (pos(headPolarity) && pos(dependantPolarity)) {
            setPolarity(dependency, Math.max(headPolarity, dependantPolarity));
        }
    }

    @Override
    public boolean applies(Dependency dependency) {
        return ACCEPTED_RELATIONS.contains(dependency.getRelation());
    }

    private double computeWordPolarity(Word word) {
        return word.getPolarity() != 0 ? word.getPolarity() : senticNetService.findWordPolarity(word);
    }
}
