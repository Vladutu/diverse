package ro.ucv.ace.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * This is a base JPA entity. All other entities must extend this class. It contains the unique identifier for each entity.
 *
 * @param <ID> type of the unique identifier
 */
@MappedSuperclass
@Getter
@Setter
public class BaseEntity<ID> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected ID id;
}
