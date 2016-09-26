package ro.ucv.ace;

/**
 * Created by Geo on 26.09.2016.
 */
public class TextSong {

    private int id;

    private String name;

    private String artist;

    public TextSong(int id, String name, String artist) {
        this.id = id;
        this.name = name;
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    @Override
    public String toString() {
        return "TextSong{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                '}';
    }
}
