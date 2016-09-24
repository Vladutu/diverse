package ro.ucv.ace.model.impl;

import ro.ucv.ace.model.ISongDetails;

import java.util.List;

/**
 * Created by Geo on 24.09.2016.
 */
public class SongDetails implements ISongDetails {

    private String id;

    private String name;

    private String artist;

    private String previewUrl;

    private List<String> albumImageUrls;

    public SongDetails(String id, String name, String artist, String previewUrl, List<String> albumImageUrls) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.previewUrl = previewUrl;
        this.albumImageUrls = albumImageUrls;
    }

    public SongDetails() {
    }

    @Override
    public String completeUrl(String baseUrl) {
        return baseUrl + id;
    }
}
