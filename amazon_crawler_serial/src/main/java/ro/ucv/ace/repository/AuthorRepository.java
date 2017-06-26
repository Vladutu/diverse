package ro.ucv.ace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.ucv.ace.entity.Author;

import java.util.List;

/**
 * Created by Geo on 13.02.2017.
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    @Query("SELECT a FROM Author a WHERE a.amazonId = ?1")
    Author findByAmazonId(String amazonId);

    @Query("SELECT a.id FROM Author a ORDER BY a.id ASC")
    List<Integer> findAllIds();

    List<Author> findByNameStartingWith(String startsWith);
}
