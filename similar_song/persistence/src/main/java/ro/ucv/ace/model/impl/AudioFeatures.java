package ro.ucv.ace.model.impl;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import ro.ucv.ace.model.IAudioFeatures;
import ro.ucv.ace.model.ISong;
import ro.ucv.ace.repository.ISongRepository;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Geo on 24.09.2016.
 */
@Configurable(preConstruction = true, dependencyCheck = true, autowire = Autowire.BY_TYPE)
@Entity
@Table(name = "AUDIO_FEATURES")
public class AudioFeatures implements IAudioFeatures {

    @Id
    @Column(name = "ID", unique = true)
    private String id;

    @Basic
    @Column(name = "DANCEABILITY", nullable = false)
    private double danceability;

    @Basic
    @Column(name = "ENERGY", nullable = false)
    private double energy;

    @Basic
    @Column(name = "S_KEY", nullable = false)
    private double key;

    @Basic
    @Column(name = "LOUDNESS", nullable = false)
    private double loudness;

    @Basic
    @Column(name = "MODE", nullable = false)
    private double mode;

    @Basic
    @Column(name = "SPEECHINESS", nullable = false)
    private double speechiness;

    @Basic
    @Column(name = "ACOUSTICNESS", nullable = false)
    private double acousticness;

    @Basic
    @Column(name = "INSTRUMENTALNESS", nullable = false)
    private double instrumentalness;

    @Basic
    @Column(name = "LIVENESS", nullable = false)
    private double liveness;

    @Basic
    @Column(name = "VALENCE", nullable = false)
    private double valence;

    @Basic
    @Column(name = "TEMPO", nullable = false)
    private double tempo;

    @Transient
    @Autowired
    private ISongRepository songRepository;


    public AudioFeatures(String id, double danceability, double energy, double key, double loudness, double mode, double speechiness,
                         double acousticness, double instrumentalness, double liveness, double valence, double tempo) {
        this.id = id;
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
    }

    public AudioFeatures() {
    }

    @Override
    public List<ISong> findSimilarSongs() {
        return songRepository.findSongsHavingSimilarAudioProperties(fieldsToArray());
    }

    private double[] fieldsToArray() {
        return new double[]{danceability, energy, key, loudness, mode, speechiness, acousticness, instrumentalness,
                liveness, valence, tempo};
    }

    @Override
    public String toString() {
        return "AudioFeatures{" +
                "id=" + id +
                ", danceability=" + danceability +
                ", energy=" + energy +
                ", key=" + key +
                ", loudness=" + loudness +
                ", mode=" + mode +
                ", speechiness=" + speechiness +
                ", acousticness=" + acousticness +
                ", instrumentalness=" + instrumentalness +
                ", liveness=" + liveness +
                ", valence=" + valence +
                ", tempo=" + tempo +
                '}';
    }
}
