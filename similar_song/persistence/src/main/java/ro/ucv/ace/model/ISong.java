package ro.ucv.ace.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import ro.ucv.ace.model.impl.Song;

import java.util.List;

/**
 * Created by Geo on 23.09.2016.
 */
@JsonSubTypes({@JsonSubTypes.Type(value = Song.class, name = "song")})
public interface ISong {

    List<ISong> findSimilarSongs(int limit);
}
