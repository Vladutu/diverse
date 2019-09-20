package ro.ucv.ace.sentiment.rule.splitSentence;

import ro.ucv.ace.parser.Dependency;
import ro.ucv.ace.parser.Sentence;
import ro.ucv.ace.parser.Word;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface SplitSentenceRule {
    Double executeRule(Sentence sentence, Function<Sentence, Double> algorithmFunction);

    boolean applies(Sentence sentence);

    default Sentence createSentence(List<Dependency> dependencies, List<Word> words) {
        List<Dependency> acceptedDependencies = dependencies.stream()
                .filter(dependency -> words.contains(dependency.getGovernor()) &&
                        words.contains(dependency.getDependent()))
                .collect(Collectors.toList());

        return new Sentence(words, acceptedDependencies, null);
    }
}
