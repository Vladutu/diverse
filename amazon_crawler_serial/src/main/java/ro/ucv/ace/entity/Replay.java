package ro.ucv.ace.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Geo on 12.02.2017.
 */
@Entity
@Table(name = "REPLAY")
public class Replay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID")
    private Author author;

    @Basic
    @Column(name = "BODY", columnDefinition = "MEDIUMTEXT")
    private String body;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "REPLAY_ID", referencedColumnName = "ID")
    private List<Replay> replays = new ArrayList<>();

    public Replay() {
    }

    public Replay(String author, String authorUrl, String amazonId, String body) {
        this.author = new Author(author, amazonId, authorUrl);
        this.body = body;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<Replay> getReplays() {
        return replays;
    }

    public void setReplays(List<Replay> replays) {
        this.replays = replays;
    }

    public void addReplay(Replay child) {
        replays.add(child);
    }
}
