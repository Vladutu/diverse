package ro.ucv.ace.joke.impl;

import ro.ucv.ace.joke.Comparator;
import ro.ucv.ace.joke.Joke;

/**
 * Created by Geo on 01.11.2016.
 */
public class CosineComparator implements Comparator {

    private Joke jokeToBeCompared;

    public CosineComparator(Joke jokeToBeCompared) {
        this.jokeToBeCompared = jokeToBeCompared;
    }

    @Override
    public double compare(Joke joke) {
        return jokeToBeCompared.computeCosineSimilarity(joke);
    }
}
