package ro.ucv.ace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ro.ucv.ace.api.ISpotifyApi;
import ro.ucv.ace.config.PersistenceConfig;
import ro.ucv.ace.exception.EntityNotFoundException;
import ro.ucv.ace.model.ISong;
import ro.ucv.ace.repository.ISongRepository;

import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 */
@Component
public class App {

    @Autowired
    private ISpotifyApi spotifyApi;

    @Autowired
    @Qualifier("songRepository")
    private ISongRepository songRepository;

    public void sayHello() {
        System.out.println("Hello world");
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        ApplicationContext cxt = new AnnotationConfigApplicationContext(PersistenceConfig.class);
        App app = (App) cxt.getBean(App.class);
        Thread.sleep(2000);
        int limit = 10;
        ISong song = null;
        try {
            song = app.songRepository.findSong("We The Kings", "Sad Song");
            List<ISong> similarSongs = song.findSimilarSongs(limit);
            for (int i = 0; i < similarSongs.size(); i++) {
                System.out.println("" + i + similarSongs.get(i));
            }


        } catch (EntityNotFoundException e) {
            System.out.println("Song not found");
        }

    }


}
