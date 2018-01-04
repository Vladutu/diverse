package ro.ucv.ace.model.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import ro.ucv.ace.model.ISongDetails;

import javax.persistence.*;

/**
 * Created by Geo on 24.09.2016.
 */
@Entity
@Table(name = "SONG_DETAILS")
@JsonTypeName("sd")
public class SongDetails implements ISongDetails {

    @Id
    @Column(name = "ID", unique = true)
    @JsonProperty
    private String id;

    @Basic
    @Column(name = "NAME", nullable = false)
    @JsonProperty
    private String name;

    @Basic
    @Column(name = "ARTIST", nullable = false)
    @JsonProperty
    private String artist;

    @Basic
    @Column(name = "PREVIEW_URL", length = 200, nullable = false, unique = true)
    @JsonProperty
    private String previewUrl;

    @Basic
    @Column(name = "ALBUM_IMAGE_URL", length = 200, nullable = false)
    @JsonProperty
    private String albumImageUrl;

    public SongDetails(String id, String previewUrl, String albumImageUrl, String artist, String name) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.previewUrl = previewUrl;
        this.albumImageUrl = albumImageUrl;
    }

    public SongDetails() {
    }

    @Override
    public String toString() {
        return "SongDetails{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", previewUrl='" + previewUrl + '\'' +
                ", albumImageUrl='" + albumImageUrl + '\'' +
                '}';
    }

    @Override
    public void setPreviewUrl(String url) {
        this.previewUrl = url;
    }

    @Override
    public String getArtist() {
        return artist;
    }

    @Override
    public String getName() {
        return name;
    }
}
