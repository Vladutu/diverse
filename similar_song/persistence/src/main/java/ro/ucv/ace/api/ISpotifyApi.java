package ro.ucv.ace.api;

import ro.ucv.ace.exception.InvalidSongException;
import ro.ucv.ace.model.ISong;

/**
 * Created by Geo on 24.09.2016.
 */
public interface ISpotifyApi {

    void authenticate();

    ISong findSong(String artistName, String songName) throws InvalidSongException;

}
