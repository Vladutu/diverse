package ro.ucv.ace.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Geo on 12.02.2017.
 */
@Entity
@Table(name = "REVIEW")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Basic
    @Column(name = "PRODUCT_RATING")
    private Double productRating;

    @Basic
    @Column(name = "TITLE", length = 700)
    private String title;

    @Basic
    @Column(name = "BODY", columnDefinition = "MEDIUMTEXT")
    private String body;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID")
    private Author author;

    @Basic
    @Column(name = "DATE")
    private String date;

    @Basic
    @Column(name = "VERIFIED_PURCHASE")
    private boolean verifiedPurchase;

    @Basic
    @Column(name = "HELPFUL_VOTES")
    private Integer helpfulVotes;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "REVIEW_ID", referencedColumnName = "ID")
    private List<Replay> replays = new ArrayList<>();

    public Review() {
    }

    public Review(Double productRating, String title, String body, String author, String amazonId, String authorUrl, String date,
                  boolean verifiedPurchase, Integer helpfulVotes, List<Replay> replays) {
        this.productRating = productRating;
        this.title = title;
        this.body = body;
        this.author = new Author(author, amazonId, authorUrl);
        this.date = date;
        this.verifiedPurchase = verifiedPurchase;
        this.helpfulVotes = helpfulVotes;
        this.replays = replays;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getProductRating() {
        return productRating;
    }

    public void setProductRating(Double productRating) {
        this.productRating = productRating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public boolean isVerifiedPurchase() {
        return verifiedPurchase;
    }

    public void setVerifiedPurchase(boolean verifiedPurchase) {
        this.verifiedPurchase = verifiedPurchase;
    }

    public Integer getHelpfulVotes() {
        return helpfulVotes;
    }

    public void setHelpfulVotes(Integer helpfulVotes) {
        this.helpfulVotes = helpfulVotes;
    }

    public List<Replay> getReplays() {
        return replays;
    }

    public void setReplays(List<Replay> replays) {
        this.replays = replays;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
