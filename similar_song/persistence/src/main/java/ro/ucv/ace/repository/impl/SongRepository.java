package ro.ucv.ace.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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
@Transactional
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
    public List<ISong> findSongsHavingSimilarAudioProperties(double[] audioProperties) {
        //TODO: code this

        return null;
    }

    private ISong merge(ISong song) {
        return entityManager.merge(song);
    }
}
