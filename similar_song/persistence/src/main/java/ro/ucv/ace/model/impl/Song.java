package ro.ucv.ace.model.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
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
@JsonTypeName("song")
public class Song implements ISong {

    @Id
    @Column(name = "ID")
    @JsonProperty
    private String id;

    @OneToOne(targetEntity = SongDetails.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "SONG_DETAILS_ID", referencedColumnName = "ID", nullable = false)
    @JsonProperty
    private ISongDetails songDetails;

    @OneToOne(targetEntity = AudioFeatures.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "AUDIO_FEATURES_ID", referencedColumnName = "ID", nullable = false)
    @JsonProperty
    private IAudioFeatures audioFeatures;


    public Song(String id, ISongDetails songDetails, IAudioFeatures audioFeatures) {
        this.id = id;
        this.songDetails = songDetails;
        this.audioFeatures = audioFeatures;
    }

    public Song() {
    }

    @Override
    public List<ISong> findSimilarSongs(int limit) {
        return audioFeatures.findSimilarSongs(limit);
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
