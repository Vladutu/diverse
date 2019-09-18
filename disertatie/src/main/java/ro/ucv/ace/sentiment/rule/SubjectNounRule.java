package ro.ucv.ace.sentiment.rule;

import ro.ucv.ace.parser.Dependency;
import ro.ucv.ace.parser.Sentence;
import ro.ucv.ace.parser.Word;
import ro.ucv.ace.senticnet.SenticNetService;

import java.util.Arrays;
import java.util.List;

import static ro.ucv.ace.sentiment.SentimentUtils.*;

public class SubjectNounRule extends RuleTemplate {

    private static final List<String> ACCEPTED_RELATIONS = Arrays.asList("nsubj", "nsubjpass");
    private static final String PASSIVE_VOICE_TOKEN = ACCEPTED_RELATIONS.get(1);
    private static final List<String> FIRST_PERSON_PRONOUNS = Arrays.asList("i", "we", "me", "us", "my", "our", "mine", "ours");

    public SubjectNounRule(SenticNetService senticNetService, boolean addRules) {
        super(senticNetService, addRules);
    }

    @Override
    public void executeRule(Dependency dependency, Sentence sentence) {
        Word head = dependency.getGovernor();
        Word dependent = dependency.getDependent();

        double dependencyPolarity = computeDependencyPolarity(dependency, sentence);
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

    private double computeDependencyPolarity(Dependency dependency, Sentence sentence) {
        Word head = dependency.getGovernor();
        Word dependent = dependency.getDependent();

        double headPolarity = computeWordPolarity(head);
        double dependantPolarity = computeWordPolarity(dependent);
        boolean passiveVoice = dependency.getRelation().equals(PASSIVE_VOICE_TOKEN);
        boolean firstPerson = sentenceIsFirstPerson(sentence.getWords());

        if (neg(headPolarity) && neg(dependantPolarity)) {
            if (passiveVoice) {
                return -Math.min(headPolarity, dependantPolarity);
            } else {
                return Math.min(headPolarity, dependantPolarity);
            }
        } else if (neg(headPolarity) && pos(dependantPolarity)) {
            if (firstPerson) {
                return Math.max(-headPolarity, dependantPolarity);
            } else {
                return Math.min(headPolarity, -dependantPolarity);
            }
        } else if (pos(headPolarity) && neg(dependantPolarity)) {
            return Math.min(-headPolarity, dependantPolarity);
        }

        return Math.max(headPolarity, dependantPolarity); //case both positive
    }

    private double computeWordPolarity(Word word) {
        return word.getPolarity() != 0 ? word.getPolarity() : senticNetService.findWordPolarity(word);
    }

    private boolean sentenceIsFirstPerson(List<Word> words) {
        return words.stream()
                .anyMatch(word -> FIRST_PERSON_PRONOUNS.stream().anyMatch(pronoun -> word.getValue().equalsIgnoreCase(pronoun)));
    }
}
