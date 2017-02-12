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

    @Basic
    @Column(name = "AUTHOR")
    private String author;

    @Basic
    @Column(name = "AUTHOR_URL", length = 700)
    private String authorUrl;

    @Basic
    @Column(name = "BODY", columnDefinition = "MEDIUMTEXT")
    private String body;

    @Basic
    @Column(name = "DATE")
    private String date;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "REPLAY_ID", referencedColumnName = "ID")
    List<Replay> replays = new ArrayList<>();

    public Replay() {
    }

    public Replay(String author, String authorUrl, String body, String date) {
        this.author = author;
        this.authorUrl = authorUrl;
        this.body = body;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorUrl() {
        return authorUrl;
    }

    public void setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Replay> getReplays() {
        return replays;
    }

    public void setReplays(List<Replay> replays) {
        this.replays = replays;
    }

    public boolean hasAuthorAndBody(String name, String body) {
        return this.author.equals(name) && this.body.equals(body);
    }

    public void addReplay(Replay child) {
        replays.add(child);
    }
}
