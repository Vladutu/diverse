package ro.ucv.ace.entity;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * Created by Geo on 27.12.2016.
 */
@NodeEntity
public class Product {

    @GraphId
    private Long id;

    private String name;

    private String asin;

    private float rating;

    @Relationship(type = "BELONGS_TO", direction = Relationship.UNDIRECTED)
    private Category category;

    public Product(String name, String asin, float rating, Category category) {
        this.name = name;
        this.asin = asin;
        this.rating = rating;
        this.category = category;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", category=" + category +
                '}';
    }
}
