package ro.ucv.ace.sentiment_analysis.grammar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Geo on 26.06.2017.
 */
public class Sentence {

    private int id;

    private List<Word> words = new ArrayList<>();

    private List<Dependency> dependencies = new ArrayList<>();

    public Sentence(int id, List<Word> words, List<Dependency> dependencies) {
        this.id = id;
        this.words = words;
        this.dependencies = dependencies;
    }

    public int getId() {
        return id;
    }

    public List<Word> getWords() {
        return words;
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }
}
