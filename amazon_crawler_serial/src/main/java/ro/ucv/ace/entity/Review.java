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

    @Basic
    @Column(name = "AUTHOR")
    private String author;

    @Basic
    @Column(name = "AUTHOR_URL", length = 700)
    private String authorUrl;

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
}
