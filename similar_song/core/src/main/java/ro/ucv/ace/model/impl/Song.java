package ro.ucv.ace.model.impl;

import ro.ucv.ace.model.IAudioFeatures;
import ro.ucv.ace.model.ISong;
import ro.ucv.ace.model.ISongDetails;

/**
 * Created by Geo on 24.09.2016.
 */
public class Song implements ISong {

    private ISongDetails songDetails;

    private IAudioFeatures audioFeatures;


    public Song(ISongDetails songDetails, IAudioFeatures audioFeatures) {
        this.songDetails = songDetails;
        this.audioFeatures = audioFeatures;
    }

    public Song() {
    }
}
