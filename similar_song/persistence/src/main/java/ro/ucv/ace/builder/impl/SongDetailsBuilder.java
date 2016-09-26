package ro.ucv.ace.builder.impl;

import org.springframework.stereotype.Component;
import ro.ucv.ace.builder.ISongDetailsBuilder;
import ro.ucv.ace.model.ISongDetails;
import ro.ucv.ace.model.impl.SongDetails;

/**
 * Created by Geo on 26.09.2016.
 */
@Component
public class SongDetailsBuilder implements ISongDetailsBuilder {
    @Override
    public ISongDetails build(String id, String previewUrl, String albumImageLink, String artistName, String songName) {
        return new SongDetails(id, previewUrl, albumImageLink, artistName, songName);
    }
}
