package ro.ucv.ace.sentiment.rule;

import ro.ucv.ace.parser.Dependency;
import ro.ucv.ace.parser.Sentence;
import ro.ucv.ace.parser.Word;
import ro.ucv.ace.senticnet.PolarityService;

import java.util.Arrays;
import java.util.List;

import static ro.ucv.ace.sentiment.SentimentUtils.*;

public class SubjectNounRule extends RuleTemplate {

    private static final List<String> ACCEPTED_RELATIONS = Arrays.asList("nsubj", "nsubjpass");
    private static final String PASSIVE_VOICE_TOKEN = ACCEPTED_RELATIONS.get(1);
    private static final List<String> FIRST_PERSON_PRONOUNS = Arrays.asList("i", "we", "me", "us", "my", "our", "mine", "ours");

    public SubjectNounRule(PolarityService polarityService, boolean addRules) {
        super(polarityService, addRules);
    }

    @Override
    public void executeRule(Dependency dependency, Sentence sentence) {
        Word head = dependency.getGovernor();
        Word dependent = dependency.getDependent();

        double dependencyPolarity = computeDependencyPolarity(dependency, sentence);
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

    private double computeDependencyPolarity(Dependency dependency, Sentence sentence) {
        Word head = dependency.getGovernor();
        Word dependent = dependency.getDependent();

        double headPolarity = head.getPolarity();
        double dependantPolarity = dependent.getPolarity();
        boolean passiveVoice = dependency.getRelation().equals(PASSIVE_VOICE_TOKEN);
        boolean firstPerson = sentenceIsFirstPerson(sentence.getWords());

        if (neg(headPolarity) && neg(dependantPolarity)) {
            if (passiveVoice) {
                return -Math.min(headPolarity, dependantPolarity);
            } else {
                return Math.min(headPolarity, dependantPolarity);
            }
        } else if (pos(headPolarity) && pos(dependantPolarity)) {
            return Math.max(headPolarity, dependantPolarity);
        } else if (neg(headPolarity) && pos(dependantPolarity)) {
            // could not find an example for first person -> positive
//            if (firstPerson) {
//                return Math.max(-headPolarity, dependantPolarity);
//            } else {
            return Math.min(headPolarity, -dependantPolarity);
//            }
        } else if (pos(headPolarity) && neg(dependantPolarity)) {
            if (passiveVoice) {
                return Math.max(headPolarity, -dependantPolarity);
            }
            return Math.min(-headPolarity, dependantPolarity);
        }

        if (headPolarity != 0) {
            return headPolarity;
        }

        return dependantPolarity;
    }

    private boolean sentenceIsFirstPerson(List<Word> words) {
        return words.stream()
                .anyMatch(word -> FIRST_PERSON_PRONOUNS.stream().anyMatch(pronoun -> word.getValue().equalsIgnoreCase(pronoun)));
    }
}
