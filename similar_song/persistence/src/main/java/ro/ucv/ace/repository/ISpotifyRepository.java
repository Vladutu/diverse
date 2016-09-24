package ro.ucv.ace.repository;

import ro.ucv.ace.exception.EntityNotFoundException;
import ro.ucv.ace.model.IAudioFeatures;
import ro.ucv.ace.model.ISongDetails;

/**
 * Created by Geo on 24.09.2016.
 */
public interface ISpotifyRepository {

    ISongDetails findSongDetails(String artistName, String songName) throws EntityNotFoundException;

    IAudioFeatures findAudioFeatures(ISongDetails songDetails);
}
