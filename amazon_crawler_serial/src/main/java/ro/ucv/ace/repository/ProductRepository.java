package ro.ucv.ace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.ucv.ace.entity.Product;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Geo on 11.02.2017.
 */
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p")
    Stream<Product> findAllAndStream();

    @Query("SELECT p.id FROM Product p")
    List<Integer> findAllIds();
}
