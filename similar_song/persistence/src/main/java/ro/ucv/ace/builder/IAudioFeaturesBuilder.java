package ro.ucv.ace.builder;

import ro.ucv.ace.model.IAudioFeatures;

/**
 * Created by Geo on 24.09.2016.
 */
public interface IAudioFeaturesBuilder {
    IAudioFeatures build(double danceability, double energy, double key, double loudness, double mode, double speechiness,
                         double acousticness, double instrumentalness, double liveness, double valence, double tempo);
}
