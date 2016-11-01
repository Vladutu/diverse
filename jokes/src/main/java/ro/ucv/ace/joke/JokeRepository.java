package ro.ucv.ace.joke;

import java.util.List;

/**
 * Created by Geo on 01.11.2016.
 */
public interface JokeRepository {

    List<Joke> findSimilarJokes(Comparator comparator);
}
