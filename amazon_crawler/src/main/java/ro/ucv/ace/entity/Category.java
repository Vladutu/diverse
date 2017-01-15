package ro.ucv.ace.entity;

import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Geo on 26.12.2016.
 */
@NodeEntity
public class Category {

    @GraphId
    private Long id;

    @Property(name = "name")
    private String name;

    @Relationship(type = "CHILD", direction = Relationship.UNDIRECTED)
    private List<Category> children = new ArrayList<>();

    @Transient
    private Category parent = null;


    public Category() {
    }

    public Category(String name) {
        this.name = name;
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

    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public void addChildren(Category... categories) {
        Collections.addAll(children, categories);
    }

    public Category getAncestor() {
        Category c = this;
        while (c.parent != null) {
            c = c.parent;
        }

        return c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Category category = (Category) o;

        return name != null ? name.equals(category.name) : category.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
