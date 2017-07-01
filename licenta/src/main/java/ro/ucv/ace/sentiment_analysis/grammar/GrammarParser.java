package ro.ucv.ace.sentiment_analysis.grammar;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * Created by Geo on 26.06.2017.
 */
@Component
public class GrammarParser {

    private StanfordCoreNLP pipeline;

    public GrammarParser() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse");
        pipeline = new StanfordCoreNLP(props);
    }

    public Document parse(String text) {
        Document document = null;
        List<Sentence> sentences = new ArrayList<>();
        int index = 1;
        Annotation annotation = new Annotation(text);
        pipeline.annotate(annotation);
        List<CoreMap> sent = annotation.get(CoreAnnotations.SentencesAnnotation.class);

        for (CoreMap s : sent) {
            List<CoreLabel> coreLabels = s.get(CoreAnnotations.TokensAnnotation.class);
            List<Word> words = new ArrayList<>();
            for (CoreLabel coreLabel : coreLabels) {
                Word word = new Word(coreLabel.index(), coreLabel.word(), coreLabel.lemma(), coreLabel.tag());
                words.add(word);
            }
            for (int i = 0; i < words.size(); i++) {
                Word current = words.get(i);
                if (i > 0) {
                    current.setPrevious(words.get(i - 1));
                }
                if (i < words.size() - 1) {
                    current.setNext(words.get(i + 1));
                }
            }


            SemanticGraph semanticGraph = s.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class);
            Collection<TypedDependency> typedDependencies = semanticGraph.typedDependencies();
            List<Dependency> dependencies = new ArrayList<>();
            for (TypedDependency typedDependency : typedDependencies) {
                String relation = typedDependency.reln().getShortName();
                if (relation.equals("root")) {
                    continue;
                }
                int govIndex = typedDependency.gov().index();
                String govValue = typedDependency.gov().value();
                int depIndex = typedDependency.dep().index();
                String depValue = typedDependency.dep().value();

                Word gov = words.stream().filter(word -> word.getIndex() == govIndex && word.getValue().equals(govValue)).findAny().get();
                Word dep = words.stream().filter(word -> word.getIndex() == depIndex && word.getValue().equals(depValue)).findAny().get();

                Dependency dependency = new Dependency(relation, gov, dep);
                dependencies.add(dependency);
            }

            Sentence sentence = new Sentence(index, words, dependencies);
            sentences.add(sentence);
        }
        document = new Document(sentences);

        return document;
    }
}
