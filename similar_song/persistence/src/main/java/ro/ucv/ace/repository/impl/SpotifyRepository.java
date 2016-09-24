package ro.ucv.ace.repository.impl;

import ro.ucv.ace.api.ISpotifyApi;
import ro.ucv.ace.exception.InvalidSongException;
import ro.ucv.ace.model.IAudioFeatures;
import ro.ucv.ace.model.ISongDetails;
import ro.ucv.ace.repository.ISpotifyRepository;

/**
 * Created by Geo on 24.09.2016.
 */
public class SpotifyRepository implements ISpotifyRepository {

    private ISpotifyApi spotifyApi;

    @Override
    public ISongDetails findSongDetails(String artistName, String songName) throws InvalidSongException {
        return spotifyApi.findSongDetails(artistName, songName);
    }

    @Override
    public IAudioFeatures findAudioFeatures(ISongDetails songDetails) {
        return spotifyApi.findAudioFeatures(songDetails);
    }
}
