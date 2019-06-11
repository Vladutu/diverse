package ro.ucv.ace.sentiment.rule;

import ro.ucv.ace.parser.Dependency;
import ro.ucv.ace.parser.Sentence;
import ro.ucv.ace.parser.Word;
import ro.ucv.ace.senticnet.SenticNetService;

import java.util.Arrays;
import java.util.List;

import static ro.ucv.ace.sentiment.rule.SentimentUtils.*;


public class SubjectNounRule implements Rule {

    private static final String PASSIVE_VOICE_TOKEN = "nsubjpass";
    private static final List<String> FIRST_PERSON_PRONOUNS = Arrays.asList("i", "we", "me", "us", "my", "our", "mine", "ours");
    private static final List<String> ACCEPTED_RELATIONS = Arrays.asList("nsubj", "nsubjpass");

    private SenticNetService senticNetService;

    public SubjectNounRule(SenticNetService senticNetService) {
        this.senticNetService = senticNetService;
    }

    @Override
    public void execute(Dependency dependency, Sentence sentence) {
        Word head = dependency.getGovernor();
        Word dependent = dependency.getDependent();

        Double conceptPolarity = senticNetService.findConceptPolarity(head, dependent);
        if (conceptPolarity != null) {
            setPolarity(dependency, conceptPolarity);
            return;
        }

        double headPolarity = senticNetService.findWordPolarity(head);
        double dependantPolarity = senticNetService.findWordPolarity(dependent);
        boolean passiveVoice = dependency.getRelation().equals(PASSIVE_VOICE_TOKEN);
        boolean firstPerson = sentenceIsFirstPerson(sentence.getWords());

        if (neg(headPolarity) && neg(dependantPolarity)) {
            if (passiveVoice) {
                setPolarity(dependency, -Math.min(headPolarity, dependantPolarity));
            } else {
                setPolarity(dependency, Math.min(headPolarity, dependantPolarity));
            }
        } else if (neg(headPolarity) && pos(dependantPolarity)) {
            if (firstPerson) {
                setPolarity(dependency, dependantPolarity);
            } else {
                setPolarity(dependency, headPolarity);
            }
        } else if (pos(headPolarity) && neg(dependantPolarity)) {
            setPolarity(dependency, dependantPolarity);
        } else if (pos(headPolarity) && pos(dependantPolarity)) {
            setPolarity(dependency, Math.max(headPolarity, dependantPolarity));
        }
    }

    @Override
    public boolean applies(Dependency dependency) {
        String relation = dependency.getRelation();

        return ACCEPTED_RELATIONS.contains(relation);
    }

    private boolean sentenceIsFirstPerson(List<Word> words) {
        return words.stream()
                .anyMatch(word -> FIRST_PERSON_PRONOUNS.stream().anyMatch(pronoun -> word.getValue().equalsIgnoreCase(pronoun)));
    }
}
