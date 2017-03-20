package ro.ucv.ace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.ucv.ace.entity.Product;

import java.util.stream.Stream;

/**
 * Created by Geo on 20.03.2017.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p")
    Stream<Product> getAll();
}
