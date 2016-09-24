package ro.ucv.ace.api.impl;

import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ro.ucv.ace.api.ISpotifyApi;
import ro.ucv.ace.builder.IAudioFeaturesBuilder;
import ro.ucv.ace.builder.ISongDetailsBuilder;
import ro.ucv.ace.exception.InvalidSongException;
import ro.ucv.ace.model.IAudioFeatures;
import ro.ucv.ace.model.ISongDetails;

import java.net.URI;

/**
 * Created by Geo on 24.09.2016.
 */
public class SpotifyApi implements ISpotifyApi {

    private String grantType;

    private String authCode;

    private String baseUrl;

    private String loginUrl;

    private String accessToken;

    private RestTemplate restTemplate;

    private ISongDetailsBuilder songDetailsBuilder;

    private IAudioFeaturesBuilder audioFeaturesBuilder;


    public SpotifyApi(String grantType, String authCode, String baseUrl, String loginUrl, RestTemplate restTemplate) {
        this.grantType = grantType;
        this.authCode = authCode;
        this.baseUrl = baseUrl;
        this.loginUrl = loginUrl;
        this.restTemplate = restTemplate;
    }

    @Override
    public void authenticate() {
        HttpEntity<String> entity = buildHeadersForAuthentication();
        ResponseEntity<String> authResult = restTemplate.exchange(loginUrl, HttpMethod.POST, entity, String.class);
        parseAuthResult(authResult);
    }


    @Override
    public ISongDetails findSongDetails(String artistName, String songName) throws InvalidSongException {
        HttpEntity<String> entity = buildHeadersForRequest();
        URI uri = buildUrlForSearchRequest(artistName, songName);

        try {
            HttpEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
            return parseSearchResponseBody(artistName, songName, response.getBody());
        } catch (Exception e) {
            throw new InvalidSongException(e.getMessage());
        }
    }

    @Override
    public IAudioFeatures findAudioFeatures(ISongDetails songDetails) {
        HttpEntity<String> entity = buildHeadersForRequest();
        String audioFeaturesUrl = buildUrlForAudioFeatures(songDetails);

        HttpEntity<String> response = restTemplate.exchange(audioFeaturesUrl, HttpMethod.GET, entity, String.class);

        return parseAudioFeaturesResponseBody(response.getBody());
    }


    private void parseAuthResult(ResponseEntity<String> authResult) {
        JSONObject authJson = new JSONObject(authResult.getBody());
        accessToken = authJson.getString("access_token");
    }

    private HttpEntity<String> buildHeadersForAuthentication() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", authCode);
        return new HttpEntity<>("grant_type=" + grantType, headers);
    }

    private HttpEntity<String> buildHeadersForRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        return new HttpEntity<>(headers);
    }

    private URI buildUrlForSearchRequest(String artistName, String songName) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + "/search")
                .queryParam("q", "track:\"" + songName + "\" artist:\"" + artistName + "\"")
                .queryParam("type", "track")
                .queryParam("limit", "1");

        return builder.build().toUri();
    }

    private ISongDetails parseSearchResponseBody(String artistName, String songName, String body) {
        JSONObject json = new JSONObject(body);

        String id = json
                .getJSONObject("tracks")
                .getJSONArray("items")
                .getJSONObject(0)
                .getString("id");
        String previewUrl = json
                .getJSONObject("tracks")
                .getJSONArray("items")
                .getJSONObject(0)
                .getString("preview_url");
        String albumImageLink = json
                .getJSONObject("tracks")
                .getJSONArray("items")
                .getJSONObject(0)
                .getJSONObject("album")
                .getJSONArray("images")
                .getJSONObject(1)
                .getString("url");

        return songDetailsBuilder.build(id, previewUrl, albumImageLink, artistName, songName);
    }

    private IAudioFeatures parseAudioFeaturesResponseBody(String body) {
        JSONObject json = new JSONObject(body);

        double danceability = json.getDouble("danceability");
        double energy = json.getDouble("energy");
        double key = json.getDouble("key");
        double loudness = json.getDouble("loudness");
        double mode = json.getDouble("mode");
        double speechiness = json.getDouble("speechiness");
        double acousticness = json.getDouble("acousticness");
        double instrumentalness = json.getDouble("instrumentalness");
        double liveness = json.getDouble("liveness");
        double valence = json.getDouble("valence");
        double tempo = json.getDouble("tempo");
        double timeSignature = json.getDouble("time_signature");

        return audioFeaturesBuilder.build(danceability, energy, key, loudness, mode, speechiness, acousticness, instrumentalness, liveness,
                valence, tempo, timeSignature);
    }

    private String buildUrlForAudioFeatures(ISongDetails songDetails) {
        return songDetails.completeUrl(baseUrl + "/audio-features/");
    }
}
