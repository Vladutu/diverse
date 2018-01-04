package ro.ucv.ace.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ucv.ace.exception.EntityNotFoundException;
import ro.ucv.ace.model.ISong;
import ro.ucv.ace.repository.ISongRepository;
import ro.ucv.ace.service.ISongService;
import ro.ucv.ace.service.YoutubeService;

import java.util.List;

/**
 * Created by Geo on 24.09.2016.
 */
@Service("songService")
@Transactional
public class SongService implements ISongService {

    @Autowired
    private ISongRepository songRepository;

    @Autowired
    private YoutubeService youtubeService;

    @Override
    public List<ISong> findSongsSimilarTo(String artistName, String songName, int limit) throws EntityNotFoundException {
        ISong song = songRepository.findSong(artistName, songName);

        List<ISong> similarSongs = song.findSimilarSongs(limit);
        for (ISong similarSong : similarSongs) {
            similarSong.setPreviewUrl(youtubeService.getEmbedYoutubeUrl(similarSong.getArtist(), similarSong.getName()));
        }

        return similarSongs;
    }
}
