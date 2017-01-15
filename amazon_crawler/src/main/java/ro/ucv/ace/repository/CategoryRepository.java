package ro.ucv.ace.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;
import ro.ucv.ace.entity.Category;

import java.util.List;

/**
 * Created by Geo on 26.12.2016.
 */
@Repository
public interface CategoryRepository extends GraphRepository<Category> {

    @Query("MATCH (c:Category) WHERE c.name = {0} return c")
    Category findByName(String name);

    @Query("MATCH (c:Category) return c")
    List<Category> findAllAsList();

    @Query("MATCH (n:Category) WHERE id(n) = {0} WITH n MATCH p=(n:Category)-[*0..]-(m:Category) RETURN p")
    Category findOneOnlyCategories(Long id);

}
