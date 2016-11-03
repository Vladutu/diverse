package ro.ucv.ace.joke.impl;

import ro.ucv.ace.joke.Joke;
import ro.ucv.ace.joke.JokeBuilder;
import ro.ucv.ace.joke.SimilarityAlgorithm;

/**
 * Created by Geo on 01.11.2016.
 */
public class JokeBuilderImpl implements JokeBuilder {

    private SimilarityAlgorithm similarityAlgorithm;

    public JokeBuilderImpl(SimilarityAlgorithm similarityAlgorithm) {
        this.similarityAlgorithm = similarityAlgorithm;
    }

    @Override
    public Joke build(String text) {
        return new JokeImpl(text, similarityAlgorithm);
    }
}
