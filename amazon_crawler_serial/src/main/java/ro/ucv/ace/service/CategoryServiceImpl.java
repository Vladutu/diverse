package ro.ucv.ace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ucv.ace.entity.Category;
import ro.ucv.ace.repository.CategoryRepository;

/**
 * Created by Geo on 11.02.2017.
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public void addCategory() {
        Category laptops = categoryRepository.findByName("Laptops");
        Category twoInOne = new Category("2 in 1 Laptops", laptops);

        categoryRepository.save(twoInOne);
    }

    @Override
    public Integer getProductsNumber(String categoryName) {
        return categoryRepository.countProductsNumber(categoryName);
    }
}
