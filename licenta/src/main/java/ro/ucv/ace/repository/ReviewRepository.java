package ro.ucv.ace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.ucv.ace.entity.Review;

import java.util.stream.Stream;

/**
 * Created by Geo on 18.03.2017.
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("SELECT r FROM Review r")
    Stream<Review> getAll();

}
