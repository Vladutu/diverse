package ro.ucv.ace.model.impl;

import ro.ucv.ace.model.ISongDetails;

import javax.persistence.*;

/**
 * Created by Geo on 24.09.2016.
 */
@Entity
@Table(name = "SONG_DETAILS")
public class SongDetails implements ISongDetails {

    @Id
    @Column(name = "ID", unique = true)
    private String id;

    @Basic
    @Column(name = "NAME", nullable = false)
    private String name;

    @Basic
    @Column(name = "ARTIST", nullable = false)
    private String artist;

    @Basic
    @Column(name = "PREVIEW_URL", length = 200, nullable = false, unique = true)
    private String previewUrl;

    @Basic
    @Column(name = "ALBUM_IMAGE_URL", length = 200, nullable = false)
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
}
