package ro.ucv.ace.model;

import java.util.List;

/**
 * Created by Geo on 23.09.2016.
 */
public interface IAudioFeatures {

    List<ISong> findSimilarSongs(int limit);
}
