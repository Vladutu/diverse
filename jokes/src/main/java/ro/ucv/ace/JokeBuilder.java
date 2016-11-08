package ro.ucv.ace;

/**
 * Created by Geo on 08.11.2016.
 */
public class JokeBuilder {

    private SimilarityAlgorithm defaultSimilarityAlgorithm;

    public JokeBuilder(SimilarityAlgorithm defaultSimilarityAlgorithm) {
        this.defaultSimilarityAlgorithm = defaultSimilarityAlgorithm;
    }

    public Joke build(String text) {
        return new JokeImpl(text, defaultSimilarityAlgorithm);
    }
}
