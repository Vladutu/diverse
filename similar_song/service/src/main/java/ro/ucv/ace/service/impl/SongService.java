package ro.ucv.ace.service.impl;

import ro.ucv.ace.exception.EntityNotFoundException;
import ro.ucv.ace.model.ISong;
import ro.ucv.ace.repository.ISongRepository;
import ro.ucv.ace.service.ISongService;

import java.util.List;

/**
 * Created by Geo on 24.09.2016.
 */
public class SongService implements ISongService {

    private ISongRepository songRepository;

    @Override
    public List<ISong> findSongsSimilarTo(String artistName, String songName) throws EntityNotFoundException {
        ISong song = songRepository.findSong(artistName, songName);

        return song.findSimilarSongs();
    }
}
