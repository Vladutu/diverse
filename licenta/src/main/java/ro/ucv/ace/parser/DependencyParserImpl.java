package ro.ucv.ace.parser;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.util.CoreMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Geo on 08.03.2017.
 */
public class DependencyParserImpl implements DependencyParser {

    private StanfordCoreNLP pipeline;

    public DependencyParserImpl() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse");
        props.setProperty("ssplit.isOneSentence", "true");
        pipeline = new StanfordCoreNLP(props);
    }

    @Override
    public List<SemanticGraphEdge> getSentenceDependencies(String text) {
        Annotation document = new Annotation(text);
        pipeline.annotate(document);

        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        CoreMap sentence = sentences.get(0);

        SemanticGraph dependencies = sentence.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class);
        List<IndexedWord> indexedWords = dependencies.vertexListSorted();
        int size = indexedWords.size();

        List<SemanticGraphEdge> edges = new ArrayList<>();
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                List<SemanticGraphEdge> goingEdges = dependencies.getAllEdges(indexedWords.get(i), indexedWords.get(j));
                List<SemanticGraphEdge> comingEdges = dependencies.getAllEdges(indexedWords.get(j), indexedWords.get(i));
                edges.addAll(goingEdges);
                edges.addAll(comingEdges);
            }
        }

        return edges;
    }
}
