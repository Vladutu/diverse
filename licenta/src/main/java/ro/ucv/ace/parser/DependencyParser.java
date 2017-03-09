package ro.ucv.ace.parser;

import edu.stanford.nlp.semgraph.SemanticGraphEdge;

import java.util.List;

/**
 * Created by Geo on 08.03.2017.
 */
public interface DependencyParser {
    List<SemanticGraphEdge> getSentenceDependencies(String text);
}
