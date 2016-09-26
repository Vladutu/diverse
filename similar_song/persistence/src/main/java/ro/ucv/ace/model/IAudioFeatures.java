package ro.ucv.ace.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import ro.ucv.ace.model.impl.AudioFeatures;

import java.util.List;

/**
 * Created by Geo on 23.09.2016.
 */
@JsonSubTypes({@JsonSubTypes.Type(value = AudioFeatures.class, name = "af")})
public interface IAudioFeatures {

    List<ISong> findSimilarSongs(int limit);
}
