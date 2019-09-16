package ro.ucv.ace.sentiment.rule;

import ro.ucv.ace.parser.Dependency;
import ro.ucv.ace.parser.Sentence;
import ro.ucv.ace.parser.Word;
import ro.ucv.ace.senticnet.SenticNetService;

import java.util.Arrays;
import java.util.List;

import static ro.ucv.ace.sentiment.rule.SentimentUtils.setPolarity;

public class DirectNominalObjectRule implements Rule {

    private static final List<String> ACCEPTED_RELATIONS = Arrays.asList("dobj"); //TODO: test "ccomp"
    private static final List<String> FIRST_PERSON_PRONOUNS = Arrays.asList("i", "we", "me", "us", "my", "our", "mine", "ours");


    private SenticNetService senticNetService;

    public DirectNominalObjectRule(SenticNetService senticNetService) {
        this.senticNetService = senticNetService;
    }

    @Override
    public void execute(Dependency dependency, Sentence sentence) {
        int firstPersonMultiplier = 1; //sentenceIsFirstPerson(sentence.getWords()) ? -1 : 1;

        Dependency otherDependency = sentence.getDependencies().stream()
                .filter(dep -> dependency.getDependent().equals(dep.getGovernor()))
                .filter(dep -> !dep.getRelation().equalsIgnoreCase("det"))
                .findFirst()
                .orElse(null);

        if (otherDependency != null) {
            Double triConceptPolarity = senticNetService.findConceptPolarity(dependency.getGovernor(),
                    dependency.getDependent(), otherDependency.getDependent());
            if (triConceptPolarity != null) {
                setPolarity(dependency, firstPersonMultiplier * triConceptPolarity);
                return;
            }

            Double duoConceptPolarity = senticNetService.findConceptPolarity(dependency.getGovernor(), dependency.getDependent());
            if (duoConceptPolarity != null) {
                setPolarity(dependency, firstPersonMultiplier * duoConceptPolarity);
                return;
            }
        }

        double governorPolarity = senticNetService.findWordPolarity(dependency.getGovernor());
        double dependentPolarity = senticNetService.findWordPolarity(dependency.getDependent());

        if (governorPolarity != 0) {
            setPolarity(dependency, firstPersonMultiplier * governorPolarity);
        } else if (dependentPolarity != 0) {
            setPolarity(dependency, firstPersonMultiplier * dependentPolarity);
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
