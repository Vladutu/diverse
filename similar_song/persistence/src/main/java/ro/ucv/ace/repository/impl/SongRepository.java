package ro.ucv.ace.repository.impl;

import ro.ucv.ace.builder.ISongBuilder;
import ro.ucv.ace.exception.EntityNotFoundException;
import ro.ucv.ace.model.IAudioFeatures;
import ro.ucv.ace.model.ISong;
import ro.ucv.ace.model.ISongDetails;
import ro.ucv.ace.repository.IJpaSongRepository;
import ro.ucv.ace.repository.ISongRepository;
import ro.ucv.ace.repository.ISpotifyRepository;

/**
 * Created by Geo on 24.09.2016.
 */
public class SongRepository implements ISongRepository {

    private IJpaSongRepository jpaSongRepository;

    private ISpotifyRepository spotifyRepository;

    private ISongBuilder songBuilder;

    @Override
    public ISong findSong(String artistName, String songName) throws EntityNotFoundException {

        try {
            return jpaSongRepository.findSong(artistName, songName);

        } catch (EntityNotFoundException e) {

            ISongDetails songDetails = spotifyRepository.findSongDetails(artistName, songName);
            IAudioFeatures audioFeatures = spotifyRepository.findAudioFeatures(songDetails);

            return songBuilder.build(songDetails, audioFeatures);
        }
    }
}
