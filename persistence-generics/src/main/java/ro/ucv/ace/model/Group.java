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
@Table(name = "GROUP_T")
@Getter
@Setter
public class Group extends BaseEntity<Integer> {

    @Basic
    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "group")
    private List<Student> students = new ArrayList<>();
}
