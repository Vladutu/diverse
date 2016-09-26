package ro.ucv.ace.builder.impl;

import org.springframework.stereotype.Component;
import ro.ucv.ace.builder.ISongBuilder;
import ro.ucv.ace.model.IAudioFeatures;
import ro.ucv.ace.model.ISong;
import ro.ucv.ace.model.ISongDetails;
import ro.ucv.ace.model.impl.Song;

/**
 * Created by Geo on 26.09.2016.
 */
@Component
public class SongBuilder implements ISongBuilder {
    @Override
    public ISong build(ISongDetails songDetails, IAudioFeatures audioFeatures) {
        return new Song(songDetails, audioFeatures);
    }
}
