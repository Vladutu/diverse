package ro.ucv.ace.entity;

import org.xml.sax.SAXException;
import ro.ucv.ace.parser.ProductDataParser;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Geo on 10.02.2017.
 */
@Entity
@Table(name = "PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "RAW_DATA", columnDefinition = "MEDIUMTEXT")
    private String rawData;

    @Basic
    @Column(name = "TITLE", length = 700)
    private String title;

    @Basic
    @Column(name = "ASIN", unique = true)
    private String asin;

    @Basic
    @Column(name = "URL", unique = true)
    private String url;

    @Basic
    @Column(name = "PRICE")
    private Double price;

    @Basic
    @Column(name = "RANK")
    private Integer rank;

    @Basic
    @Column(name = "RATING")
    private Double rating;

    @Basic
    @Column(name = "REVIEW_URL", length = 500)
    private String reviewsUrl;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID")
    private Category category;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID")
    private List<Feature> features = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID")
    private List<Review> reviews = new ArrayList<>();

    public Product() {
    }

    public Product(String asin, String title, String url, String rawData, Category category) {
        this.asin = asin;
        this.title = title;
        this.url = url;
        this.rawData = rawData;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public String getReviewsUrl() {
        return reviewsUrl;
    }

    public void setReviewsUrl(String reviewsUrl) {
        this.reviewsUrl = reviewsUrl;
    }

    public void parse(ProductDataParser productDataParser) throws IOException, SAXException {
        productDataParser.parse(this);
    }

    public String getPrimaryReviewUrl() {
        String revUrl = url.replace("/dp/", "/product-reviews/");
        revUrl += "?reviewerType=all_reviews&pageNumber=";

        return revUrl;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void addReviews(List<Review> reviews) {
        this.reviews.addAll(reviews);
    }

    public void updateRating(Double productOverallRating) {
        if (productOverallRating != null) {
            rating = productOverallRating;
        }
    }
}
