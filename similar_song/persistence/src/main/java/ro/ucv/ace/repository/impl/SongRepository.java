package ro.ucv.ace.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ro.ucv.ace.api.ISpotifyApi;
import ro.ucv.ace.config.JinqSource;
import ro.ucv.ace.exception.EntityNotFoundException;
import ro.ucv.ace.exception.InvalidSongException;
import ro.ucv.ace.model.ISong;
import ro.ucv.ace.repository.ISongRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


/**
 * Created by Geo on 26.09.2016.
 */
@Repository("songRepository")
public class SongRepository implements ISongRepository {

    @Autowired
    private ISpotifyApi spotifyApi;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JinqSource jinqSource;

    @Override
    public ISong findSong(String artistName, String songName) throws EntityNotFoundException {
        try {
            ISong song = spotifyApi.findSong(artistName, songName);

            return merge(song);
        } catch (InvalidSongException e) {
            throw new EntityNotFoundException("Song not found in the database and with the spotify api");
        }
    }

    @Override
    public List<ISong> findSongsHavingSimilarAudioProperties(double[] audioProperties, int limit) {
        return entityManager.createQuery("SELECT a FROM Song a ORDER BY " + orderByQuery(audioProperties)
                , ISong.class)
                .setFirstResult(1)
                .setMaxResults(limit)
                .getResultList();
    }

    private ISong merge(ISong song) {
        return entityManager.merge(song);
    }

    private String orderByQuery(double[] audioProperties) {
        String orderBy = "ABS((a.audioFeatures.danceability * :danceability + a.audioFeatures.energy * :energy + a.audioFeatures.sKey * :key + " +
                "a.audioFeatures.loudness * :loudness + a.audioFeatures.speechiness * :speechiness + a.audioFeatures.acousticness * :acousticness + " +
                "a.audioFeatures.instrumentalness * :instrumentalness + a.audioFeatures.liveness * :liveness + a.audioFeatures.valence * :valence +" +
                "a.audioFeatures.tempo * :tempo) / " +
                "(" +
                "SQRT(a.audioFeatures.danceability * a.audioFeatures.danceability + a.audioFeatures.energy * a.audioFeatures.energy + a.audioFeatures.sKey * a.audioFeatures.sKey" +
                " + a.audioFeatures.loudness * a.audioFeatures.loudness +" +
                "a.audioFeatures.speechiness * a.audioFeatures.speechiness + a.audioFeatures.acousticness * a.audioFeatures.acousticness +" +
                " a.audioFeatures.instrumentalness * a.audioFeatures.instrumentalness +" +
                " a.audioFeatures.liveness * a.audioFeatures.liveness + a.audioFeatures.valence * a.audioFeatures.valence" +
                " + a.audioFeatures.tempo * a.audioFeatures.tempo) / " +
                "SQRT(:danceability * :danceability + :energy * :energy + :key * :key + :loudness * :loudness + " +
                ":speechiness * :speechiness + :acousticness * :acousticness + :instrumentalness * :instrumentalness " +
                "+ :liveness * :liveness + :valence * :valence + :tempo * :tempo)" +
                ")) DESC";
        orderBy = orderBy.replaceAll(":danceability", "(" + String.valueOf(audioProperties[0]) + ")");
        orderBy = orderBy.replaceAll(":energy", "(" + String.valueOf(audioProperties[1]) + ")");
        orderBy = orderBy.replaceAll(":key", "(" + String.valueOf(audioProperties[2]) + ")");
        orderBy = orderBy.replaceAll(":loudness", "(" + String.valueOf(audioProperties[3]) + ")");
        orderBy = orderBy.replaceAll(":speechiness", "(" + String.valueOf(audioProperties[4]) + ")");
        orderBy = orderBy.replaceAll(":acousticness", "(" + String.valueOf(audioProperties[5]) + ")");
        orderBy = orderBy.replaceAll(":instrumentalness", "(" + String.valueOf(audioProperties[6]) + ")");
        orderBy = orderBy.replaceAll(":liveness", "(" + String.valueOf(audioProperties[7]) + ")");
        orderBy = orderBy.replaceAll(":valence", "(" + String.valueOf(audioProperties[8]) + ")");
        orderBy = orderBy.replaceAll(":tempo", "(" + String.valueOf(audioProperties[9]) + ")");

        return orderBy;
    }
}
