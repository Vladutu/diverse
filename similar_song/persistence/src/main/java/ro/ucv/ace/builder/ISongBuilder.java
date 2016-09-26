package ro.ucv.ace.builder;

import ro.ucv.ace.model.IAudioFeatures;
import ro.ucv.ace.model.ISong;
import ro.ucv.ace.model.ISongDetails;

/**
 * Created by Geo on 24.09.2016.
 */
public interface ISongBuilder {

    ISong build(ISongDetails songDetails, IAudioFeatures audioFeatures);
}
