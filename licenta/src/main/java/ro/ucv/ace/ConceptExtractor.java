package ro.ucv.ace;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Geo on 05.03.2017.
 */
public class ConceptExtractor {

    private ClausesExtractor clausesExtractor = new ClausesExtractor();

    public List<String> extractConcepts(String sent) {
        List<String> concepts = new ArrayList<>();
        List<String> clauses = clausesExtractor.extractClauses(sent);
        System.out.println(clauses);
        return concepts;
    }


}
