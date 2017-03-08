package ro.ucv.ace.repository;

import ro.ucv.ace.model.SenticConcept;

import java.util.Optional;

/**
 * Created by Geo on 05.03.2017.
 */
public interface SenticRepository {

    Optional<SenticConcept> findConcept(String name);
}
