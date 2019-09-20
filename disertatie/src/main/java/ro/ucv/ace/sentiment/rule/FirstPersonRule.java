package ro.ucv.ace.sentiment.rule;

import ro.ucv.ace.parser.Dependency;
import ro.ucv.ace.parser.Sentence;
import ro.ucv.ace.parser.Word;

import java.util.Arrays;
import java.util.List;

public class FirstPersonRule {

    private static final List<String> FIRST_PERSON_PRONOUNS = Arrays.asList("i", "we", "me", "us", "my", "our", "mine", "ours");
    private static final List<String> VERB_POS = Arrays.asList("VB", "VBD", "VBG", "VBN", "VBP", "VBZ");

    public double execute(Sentence sentence) {
        Dependency dependency = subjectFirstPersonDependency(sentence);
        if (dependency == null) {
            return 0;
        }

        Word verb = extractVerb(dependency);
        if (verb == null) {
            return 0;
        }

        return verb.getPolarity();
    }

    private Word extractVerb(Dependency dependency) {
        if (wordIsVerb(dependency.getDependent())) {
            return dependency.getDependent();
        }
        if (wordIsVerb(dependency.getGovernor())) {
            return dependency.getGovernor();
        }

        return null;
    }

    private Dependency subjectFirstPersonDependency(Sentence sentence) {
        List<Dependency> dependencies = sentence.getDependencies();

        return dependencies.stream()
                .filter(dep -> dep.getRelation().matches(".*subj.*"))
                .filter(dep -> wordIsFirstPerson(dep.getGovernor()) || wordIsFirstPerson(dep.getDependent()))
                .findFirst()
                .orElse(null);
    }

    private boolean wordIsVerb(Word word) {
        return VERB_POS.stream().anyMatch(pos -> word.getPos().equalsIgnoreCase(pos));
    }

    private boolean wordIsFirstPerson(Word word) {
        return FIRST_PERSON_PRONOUNS.stream().anyMatch(pronoun -> word.getValue().equalsIgnoreCase(pronoun));
    }
}
