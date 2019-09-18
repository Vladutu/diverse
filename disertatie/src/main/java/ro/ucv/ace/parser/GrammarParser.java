package ro.ucv.ace.parser;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Component
public class GrammarParser {

    private static final String ROOT_RELATION = "root";
    private static final String NEGATION_RELATION = "neg";

    private StanfordCoreNLP pipeline;

    public GrammarParser() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse");
        props.setProperty("parse.originalDependencies", "true");
        pipeline = new StanfordCoreNLP(props);
    }

    public Sentence parse(String text) {
        Annotation annotation = new Annotation(text);
        pipeline.annotate(annotation);
        List<CoreMap> sent = annotation.get(CoreAnnotations.SentencesAnnotation.class);

        if (sent.size() > 1) {
            throw new RuntimeException("Multi-sentence text");
        }

        CoreMap s = sent.get(0);
        List<CoreLabel> coreLabels = s.get(CoreAnnotations.TokensAnnotation.class);
        List<Word> words = coreLabels.stream()
                .map(coreLabel -> new Word(coreLabel.index(), coreLabel.word(), coreLabel.lemma(), coreLabel.tag()))
                .collect(Collectors.toList());

        setCurrentAndPreviousWords(words);

        SemanticGraph semanticGraph = s.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class);
        Collection<TypedDependency> typedDependencies = semanticGraph.typedDependencies();

        List<Dependency> dependencies = typedDependencies.stream()
                .filter(typedDependency -> !typedDependency.reln().getShortName().equals(ROOT_RELATION))
                .map(typedDependency -> toDependency(typedDependency, words))
                .collect(Collectors.toList());

        return new Sentence(words, dependencies);
    }

    private Dependency toDependency(TypedDependency typedDependency, List<Word> words) {
        String relation = typedDependency.reln().getShortName();
        int govIndex = typedDependency.gov().index();
        String govValue = typedDependency.gov().value();
        int depIndex = typedDependency.dep().index();
        String depValue = typedDependency.dep().value();

        Word governor = words.stream().filter(word -> word.getIndex() == govIndex && word.getValue().equals(govValue)).findAny().get();
        Word dependant = words.stream().filter(word -> word.getIndex() == depIndex && word.getValue().equals(depValue)).findAny().get();

        if (relation.equals(NEGATION_RELATION)) {
            governor.setNegated(true);
        }

        return new Dependency(relation, governor, dependant);
    }

    private void setCurrentAndPreviousWords(List<Word> words) {
        for (int i = 0; i < words.size(); i++) {
            Word current = words.get(i);
            if (i > 0) {
                current.setPrevious(words.get(i - 1));
            }
            if (i < words.size() - 1) {
                current.setNext(words.get(i + 1));
            }
        }
    }
}
