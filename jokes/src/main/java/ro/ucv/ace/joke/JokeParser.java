package ro.ucv.ace.joke;

import java.io.File;

/**
 * Created by Geo on 01.11.2016.
 */
public interface JokeParser {

    Joke parse(File file);
}
