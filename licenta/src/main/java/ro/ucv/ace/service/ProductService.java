package ro.ucv.ace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ucv.ace.entity.Review;
import ro.ucv.ace.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Geo on 20.03.2017.
 */
@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void removeEmptyReviewsOrReplays() {
        productRepository.getAll().forEach(product -> {
            List<Review> reviews = product.getReviews();
            List<Review> toBeRemoved = new ArrayList<>();

            reviews.forEach(review -> {
                if (review.getBody().trim().isEmpty()) {
                    toBeRemoved.add(review);
                    System.out.println("Emtpy review with id " + review.getId());
                }

            });

            reviews.removeAll(toBeRemoved);
        });
    }


}
