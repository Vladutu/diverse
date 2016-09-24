package ro.ucv.ace.repository;

import ro.ucv.ace.exception.EntityNotFoundException;
import ro.ucv.ace.model.ISong;

/**
 * Created by Geo on 24.09.2016.
 */
public interface IJpaSongRepository {

    ISong findSong(String artistName, String songName) throws EntityNotFoundException;
}
