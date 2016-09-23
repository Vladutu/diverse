package ro.ucv.ace.repository;

import ro.ucv.ace.model.IAudioFeatures;
import ro.ucv.ace.model.ISong;

/**
 * Created by Geo on 23.09.2016.
 */
public interface ISongRepository {

    ISong findSong(String artistName, String songName);

    IAudioFeatures findAudioFeatures(String songId);
}
