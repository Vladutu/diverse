package ro.ucv.ace.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Geo on 28.05.2016.
 */
@Entity
@Table(name = "PERSON")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DISC", discriminatorType = DiscriminatorType.CHAR)
@DiscriminatorValue("P")
@Getter
@Setter
public class Person extends BaseEntity<Integer> {

    @Basic
    @Column(name = "SSN", length = 13, nullable = false, unique = true)
    protected String ssn;

    @Basic
    @Column(name = "FIRST_NAME", nullable = false)
    protected String firstName;

    @Basic
    @Column(name = "LAST_NAME", nullable = false)
    protected String lastName;

}
