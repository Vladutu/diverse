package ro.ucv.ace;

import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import ro.ucv.ace.parser.DependencyParser;
import ro.ucv.ace.parser.DependencyParserImpl;

import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        DependencyParser dependencyParser = new DependencyParserImpl();
        List<SemanticGraphEdge> sentenceDependencies = dependencyParser.getSentenceDependencies("This car is nice but expensive");
        System.out.println(sentenceDependencies);
        sentenceDependencies.forEach(s -> System.out.println(s.getRelation().getLongName()));
    }
}
