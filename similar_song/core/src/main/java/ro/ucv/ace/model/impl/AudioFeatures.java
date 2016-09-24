package ro.ucv.ace.model.impl;

import ro.ucv.ace.model.IAudioFeatures;

/**
 * Created by Geo on 24.09.2016.
 */
public class AudioFeatures implements IAudioFeatures {

    private double danceability;

    private double energy;

    private double key;

    private double loudness;

    private double mode;

    private double speechiness;

    private double acousticness;

    private double instrumentalness;

    private double liveness;

    private double valence;

    private double tempo;

    private double timeSignature;


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
