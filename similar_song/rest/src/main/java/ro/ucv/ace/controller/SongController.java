package ro.ucv.ace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.ucv.ace.exception.EntityNotFoundException;
import ro.ucv.ace.model.ISong;
import ro.ucv.ace.service.ISongService;

import java.util.List;

/**
 * Created by Geo on 26.09.2016.
 */
@RestController
@RequestMapping(value = "/")
public class SongController {

    @Autowired
    private ISongService songService;

    @RequestMapping(value = "/song", method = RequestMethod.GET)
    public ResponseEntity<List<ISong>> findSimilarSongs(@RequestParam(name = "song", required = true) String song,
                                                        @RequestParam(name = "artist", required = true) String artist,
                                                        @RequestParam(name = "limit", required = true) int limit) {
        List<ISong> songsSimilarTo = null;
        try {
            songsSimilarTo = songService.findSongsSimilarTo(artist, song, limit);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(songsSimilarTo, HttpStatus.OK);
    }

}
