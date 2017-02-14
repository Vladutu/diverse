package ro.ucv.ace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.ucv.ace.entity.Category;

/**
 * Created by Geo on 11.02.2017.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category findByName(String name);

    @Query("SELECT SIZE(c.products) FROM Category c WHERE c.name = ?1")
    Integer countProductsNumber(String categoryName);
}
