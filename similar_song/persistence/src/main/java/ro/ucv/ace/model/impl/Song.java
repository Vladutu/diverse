package ro.ucv.ace.model.impl;

import ro.ucv.ace.model.IAudioFeatures;
import ro.ucv.ace.model.ISong;
import ro.ucv.ace.model.ISongDetails;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Geo on 24.09.2016.
 */
@Entity
@Table(name = "SONG")
public class Song implements ISong {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne(targetEntity = SongDetails.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "SONG_DETAILS_ID", referencedColumnName = "ID", nullable = false)
    private ISongDetails songDetails;

    @OneToOne(targetEntity = AudioFeatures.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "AUDIO_FEATURES_ID", referencedColumnName = "ID", nullable = false)
    private IAudioFeatures audioFeatures;


    public Song(ISongDetails songDetails, IAudioFeatures audioFeatures) {
        this.songDetails = songDetails;
        this.audioFeatures = audioFeatures;
    }

    public Song() {
    }

    @Override
    public List<ISong> findSimilarSongs() {
        return audioFeatures.findSimilarSongs();
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", songDetails=" + songDetails +
                ", audioFeatures=" + audioFeatures +
                '}';
    }
}
