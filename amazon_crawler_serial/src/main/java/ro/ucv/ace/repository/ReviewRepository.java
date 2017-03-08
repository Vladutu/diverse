package ro.ucv.ace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ucv.ace.entity.Review;

/**
 * Created by Geo on 21.02.2017.
 */
public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
