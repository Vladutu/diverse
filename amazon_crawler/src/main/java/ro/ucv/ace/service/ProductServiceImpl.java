package ro.ucv.ace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ucv.ace.entity.Category;
import ro.ucv.ace.entity.Product;
import ro.ucv.ace.repository.CategoryRepository;
import ro.ucv.ace.repository.ProductRepository;

/**
 * Created by Geo on 27.12.2016.
 */
@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Product save(Product product) {
        Category category = categoryRepository.findByName(product.getCategory().getName());
        product.setCategory(category);

        return productRepository.save(product);
    }

}
