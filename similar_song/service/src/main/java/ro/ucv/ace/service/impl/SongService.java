package ro.ucv.ace.service.impl;

import ro.ucv.ace.builder.ISongBuilder;
import ro.ucv.ace.exception.EntityNotFoundException;
import ro.ucv.ace.exception.InvalidSongException;
import ro.ucv.ace.model.IAudioFeatures;
import ro.ucv.ace.model.ISong;
import ro.ucv.ace.model.ISongDetails;
import ro.ucv.ace.repository.ISongRepository;
import ro.ucv.ace.repository.ISpotifyRepository;
import ro.ucv.ace.service.ISongService;

import java.util.List;

/**
 * Created by Geo on 24.09.2016.
 */
public class SongService implements ISongService {

    private ISpotifyRepository spotifyRepository;

    private ISongRepository songRepository;

    private ISongBuilder songBuilder;

    @Override
    public List<ISong> findSongsSimilarTo(String artistName, String songName) throws InvalidSongException {
        ISongDetails songDetails = spotifyRepository.findSongDetails(artistName, songName);
        ISong song = null;

        try {
            song = songRepository.findSong(songDetails);
        } catch (EntityNotFoundException e) {
            IAudioFeatures audioFeatures = spotifyRepository.findAudioFeatures(songDetails);
            song = songBuilder.build(songDetails, audioFeatures);
            song = songRepository.save(song);
        } finally {
            return songRepository.findSimilarSongs(song);
        }
    }
}
