package ro.ucv.ace.builder.impl;

import org.springframework.stereotype.Component;
import ro.ucv.ace.builder.IAudioFeaturesBuilder;
import ro.ucv.ace.model.IAudioFeatures;
import ro.ucv.ace.model.impl.AudioFeatures;

/**
 * Created by Geo on 26.09.2016.
 */
@Component
public class AudioFeaturesBuilder implements IAudioFeaturesBuilder {
    @Override
    public IAudioFeatures build(String id, double danceability, double energy, double key, double loudness, double mode,
                                double speechiness, double acousticness, double instrumentalness, double liveness,
                                double valence, double tempo) {
        return  new AudioFeatures(id,danceability, energy,key,loudness,mode,speechiness, acousticness,instrumentalness,
                liveness,valence,tempo);
    }
}
