package ro.ucv.ace.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import ro.ucv.ace.model.impl.SongDetails;

/**
 * Created by Geo on 24.09.2016.
 */
@JsonSubTypes({@JsonSubTypes.Type(value = SongDetails.class, name = "sd")})
public interface ISongDetails {
}
