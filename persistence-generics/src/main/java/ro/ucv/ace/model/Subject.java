package ro.ucv.ace.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Geo on 28.05.2016.
 */
@Entity
@Table(name = "SUBJECT")
@Getter
@Setter
public class Subject extends BaseEntity {

    @Basic
    @Column(name = "NAME", nullable = false)
    private String name;

    @Basic
    @Column(name = "CREDITS", nullable = false)
    private Integer credits;

    @ManyToMany(mappedBy = "subjects")
    private List<Student> students = new ArrayList<>();
}
