package ro.ucv.ace.joke.impl;

import ro.ucv.ace.joke.Joke;
import ro.ucv.ace.joke.JokeBuilder;

/**
 * Created by Geo on 01.11.2016.
 */
public class JokeBuilderImpl implements JokeBuilder {

    @Override
    public Joke build(String text) {
        return new JokeImpl(text);
    }
}
