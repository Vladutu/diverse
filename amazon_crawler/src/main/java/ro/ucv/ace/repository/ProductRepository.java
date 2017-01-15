package ro.ucv.ace.repository;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;
import ro.ucv.ace.entity.Product;

/**
 * Created by Geo on 27.12.2016.
 */
@Repository
public interface ProductRepository extends GraphRepository<Product> {
}
