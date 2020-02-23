package ro.ucv.ace.review;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Review {
    @Id
    private Integer id;

    @Column(name = "BODY")
    private String body;
    @Column(name = "DATE")
    private String date;
    @Column(name = "HELPFUL_VOTES")
    private int helpfulVotes;
    @Column(name = "PRODUCT_RATING")
    private int productRating;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "VERIFIED_PURCHASE")
    private boolean verifiedPurchase;

}
