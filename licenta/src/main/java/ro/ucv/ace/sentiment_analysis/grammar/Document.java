package ro.ucv.ace.sentiment_analysis.grammar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Geo on 26.06.2017.
 */
public class Document {

    private List<Sentence> sentences = new ArrayList<>();

    public Document(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }
}
