package ro.ucv.ace.entity;

import javax.persistence.*;

/**
 * Created by Geo on 13.02.2017.
 */
@Entity
@Table(name = "AUTHOR")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Basic
    @Column(name = "NAME", length = 500)
    private String name;

    @Basic
    @Column(name = "AMAZON_ID", length = 400, unique = true)
    private String amazonId;

    @Basic
    @Column(name = "URL", length = 700)
    private String url;

    public Author() {
    }

    public Author(String name, String amazonId, String url) {
        this.name = name;
        this.amazonId = amazonId;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAmazonId() {
        return amazonId;
    }

    public void setAmazonId(String amazonId) {
        this.amazonId = amazonId;
    }
}
