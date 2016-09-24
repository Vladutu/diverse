package ro.ucv.ace.repository;

import ro.ucv.ace.exception.InvalidSongException;
import ro.ucv.ace.model.IAudioFeatures;
import ro.ucv.ace.model.ISongDetails;

/**
 * Created by Geo on 24.09.2016.
 */
public interface ISpotifyRepository {

    ISongDetails findSongDetails(String artistName, String songName) throws InvalidSongException;

    IAudioFeatures findAudioFeatures(ISongDetails songDetails);
}
