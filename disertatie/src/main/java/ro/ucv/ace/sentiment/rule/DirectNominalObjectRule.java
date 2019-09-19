package ro.ucv.ace.sentiment.rule;

import ro.ucv.ace.parser.Dependency;
import ro.ucv.ace.parser.Sentence;
import ro.ucv.ace.parser.Word;
import ro.ucv.ace.senticnet.SenticNetService;

import java.util.Arrays;
import java.util.List;

import static ro.ucv.ace.sentiment.SentimentUtils.setPolarity;

public class DirectNominalObjectRule extends RuleTemplate {

    private static final List<String> ACCEPTED_RELATIONS = Arrays.asList("dobj");
    private static final List<String> FIRST_PERSON_PRONOUNS = Arrays.asList("i", "we", "me", "us", "my", "our", "mine", "ours");

    public DirectNominalObjectRule(SenticNetService senticNetService, boolean addRules) {
        super(senticNetService, addRules);
    }

    @Override
    public void executeRule(Dependency dependency, Sentence sentence) {
        int firstPersonSubjectMultiplier = subjectIsFirstPerson(sentence) ? -1 : 1;
        double dependencyPolarity = computeDependencyPolarity(dependency, firstPersonSubjectMultiplier);

        Double conceptPolarity = senticNetService.findConceptPolarity(dependency.getGovernor(), dependency.getDependent());
        if (conceptPolarity != null) {
            int reversePolarityFactor = dependencyPolarity * conceptPolarity < 0 ? -1 : 1;
            setPolarity(dependency, firstPersonSubjectMultiplier * reversePolarityFactor * conceptPolarity);
            return;
        }

        setPolarity(dependency, firstPersonSubjectMultiplier * dependencyPolarity);
    }

    @Override
    public boolean applies(Dependency dependency) {
        String relation = dependency.getRelation();

        return ACCEPTED_RELATIONS.contains(relation);
    }

    private double computeDependencyPolarity(Dependency dependency, int firstPersonSubjectMultiplier) {
        double governorPolarity = dependency.getGovernor().getPolarity();
        double dependentPolarity = dependency.getDependent().getPolarity();

        if (governorPolarity != 0) {
            return firstPersonSubjectMultiplier * governorPolarity;
        } else {
            return firstPersonSubjectMultiplier * dependentPolarity;
        }
    }

    private boolean subjectIsFirstPerson(Sentence sentence) {
        List<Dependency> dependencies = sentence.getDependencies();

        return dependencies.stream()
                .filter(dep -> dep.getRelation().matches(".*subj.*"))
                .anyMatch(dep -> wordIsFirstPerson(dep.getGovernor()) || wordIsFirstPerson(dep.getDependent()));
    }

    private boolean wordIsFirstPerson(Word word) {
        return FIRST_PERSON_PRONOUNS.stream().anyMatch(pronoun -> word.getValue().equalsIgnoreCase(pronoun));
    }
}
