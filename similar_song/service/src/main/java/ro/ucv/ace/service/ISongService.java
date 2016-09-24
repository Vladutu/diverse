package ro.ucv.ace.service;

import ro.ucv.ace.exception.InvalidSongException;
import ro.ucv.ace.model.ISong;

import java.util.List;

/**
 * Created by Geo on 24.09.2016.
 */
public interface ISongService {

    List<ISong> findSongsSimilarTo(String artistName, String songName) throws InvalidSongException;
}
