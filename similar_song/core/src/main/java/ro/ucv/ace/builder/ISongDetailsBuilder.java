package ro.ucv.ace.builder;

import ro.ucv.ace.model.ISongDetails;

/**
 * Created by Geo on 24.09.2016.
 */
public interface ISongDetailsBuilder {
    ISongDetails build(String id, String previewUrl, String albumImageLink, String artistName, String songName);
}
