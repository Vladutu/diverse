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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

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

//        ISong song = null;
//        try {
//            song = app.songRepository.findSong("The Black Eyed Peas", "Imma Be");
//        } catch (EntityNotFoundException e) {
//            System.out.println("Song not found");
//        }
//        System.out.println(song);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream("D:\\songs.txt"), "ISO-8859-15"))) {
            String line;
            while ((line = br.readLine()) != null) {
                TextSong ts = parseLine(line);
                try {
                    ISong song = app.songRepository.findSong(ts.getArtist(), ts.getName());
                } catch (EntityNotFoundException e) {
                    System.out.println("Song not found");
                }

                Thread.sleep(200);

            }
        }

    }

    private static TextSong parseLine(String line) {

        String stringId = line.split(" ")[0];
        String rest = line.substring(stringId.length());
        String[] restTokens = rest.split("::");

        int id = Integer.parseInt(stringId);
        String name = restTokens[0].trim();
        String artist = restTokens[1].trim();

        artist = swapEnding(artist, ", The", "The ");
        artist = swapEnding(artist, ", A", "A ");

        return new TextSong(id, name, artist);
    }

    private static String swapEnding(String artist, String ending, String start) {
        if (artist.endsWith(ending)) {
            String keep = artist.split(ending)[0];
            artist = start + keep;
        }
        return artist;
    }
}
