package ro.ucv.ace.repository;

import ro.ucv.ace.exception.EntityNotFoundException;
import ro.ucv.ace.model.ISong;
import ro.ucv.ace.model.ISongDetails;

import java.util.List;

/**
 * Created by Geo on 23.09.2016.
 */
public interface ISongRepository {

    ISong findSong(ISongDetails songDetails) throws EntityNotFoundException;

    List<ISong> findSimilarSongs(ISong song);

    ISong save(ISong newSong);
}
