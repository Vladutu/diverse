package ro.ucv.ace.model.impl;

import ro.ucv.ace.model.IAudioFeatures;

/**
 * Created by Geo on 24.09.2016.
 */
public class AudioFeatures implements IAudioFeatures {

    private float danceability;

    private float energy;

    private float key;

    private float loudness;

    private float mode;

    private float speechiness;

    private float acousticness;

    private float instrumentalness;

    private float liveness;

    private float valence;

    private float tempo;

    private float timeSignature;


    public AudioFeatures(float danceability, float energy, float key, float loudness, float mode, float speechiness,
                         float acousticness, float instrumentalness, float liveness, float valence, float tempo,
                         float timeSignature) {
        this.danceability = danceability;
        this.energy = energy;
        this.key = key;
        this.loudness = loudness;
        this.mode = mode;
        this.speechiness = speechiness;
        this.acousticness = acousticness;
        this.instrumentalness = instrumentalness;
        this.liveness = liveness;
        this.valence = valence;
        this.tempo = tempo;
        this.timeSignature = timeSignature;
    }

    public AudioFeatures() {
    }
}
