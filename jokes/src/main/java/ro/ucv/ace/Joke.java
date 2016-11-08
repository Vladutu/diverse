package ro.ucv.ace;

import java.util.Map;

/**
 * Created by Geo on 08.11.2016.
 */
public interface Joke {
    Double compare(Joke joke);

    Map<String, Integer> getFrequency();
}
