package ro.ucv.ace.joke;

import java.util.Map;

/**
 * Created by Geo on 01.11.2016.
 */
public interface Joke {
    double computeCosineSimilarity(Joke joke);
    double computeCosineSimilarity(Map<String, Integer> wordsMap);
}
