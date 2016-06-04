package ro.ucv.ace.model;

import lombok.Getter;
import lombok.Setter;
import ro.ucv.ace.enums.Subgroup;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Geo on 28.05.2016.
 */
@Entity
@Table(name = "STUDENT")
@DiscriminatorValue("S")
@Getter
@Setter
public class Student extends Person {

    @Basic
    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "SUBGROUP", nullable = false)
    private Subgroup subgroup;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "STUDENT_SUBJECT",
            joinColumns = {@JoinColumn(name = "STUDENT_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "SUBJECT_ID", referencedColumnName = "ID")}
    )
    private List<Subject> subjects = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "GROUP_ID", referencedColumnName = "ID", nullable = true)
    private Group group;

    public Student() {
    }

    public Student(String ssn, String firstName, String lastName, String email, Subgroup subgroup) {
        this.ssn = ssn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.subgroup = subgroup;
    }

    @Override
    public String toString() {
        return "Student{" +
                "email='" + email + '\'' +
                ", subgroup=" + subgroup +
                ", group=" + group +
                '}';
    }
}
