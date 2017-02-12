package ro.ucv.ace.entity;

import javax.persistence.*;

/**
 * Created by Geo on 11.02.2017.
 */
@Entity
@Table(name = "FEATURE")
public class Feature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Basic
    @Column(name = "NAME", columnDefinition = "MEDIUMTEXT")
    private String name;

    public Feature() {
    }

    public Feature(String name) {
        this.name = name;
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
}
