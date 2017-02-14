package ro.ucv.ace.service;

import ro.ucv.ace.entity.Category;

/**
 * Created by Geo on 11.02.2017.
 */
public interface CategoryService {

    Category save(Category category);

    Category findByName(String name);

    void addCategory();

    Integer getProductsNumber(String categoryName);
}
