package ro.ucv.ace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.ucv.ace.entity.Author;

/**
 * Created by Geo on 25.03.2017.
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {


}
